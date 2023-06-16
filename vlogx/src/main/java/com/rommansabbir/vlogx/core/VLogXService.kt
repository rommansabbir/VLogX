package com.rommansabbir.vlogx.core

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.commander.Command
import com.rommansabbir.commander.Commander
import com.rommansabbir.commander.CommanderManager
import com.rommansabbir.vlogx.R

class VLogXService : Service() {
    companion object {
        const val UID = "VLogXService_UID"
        const val COMMAND = "VLogXService_NEW_LOG"

        @Volatile
        private var isViewInitialized: Boolean = false

        @Volatile
        private var application: Application? = null

        fun init(application: Application) {
            this.application = application
            try {
                application.startService(Intent(application, VLogXService::class.java))
            } catch (e: Exception) {
                this.application = null
                e.printStackTrace()
            }
        }
    }

    private var view: View? = null
    private var params: WindowManager.LayoutParams? = null
    private var windowManager: WindowManager? = null
    private var layoutInflater: LayoutInflater? = null
    private val adapter = LoggerXAdapter()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun checkOverlayDisplayPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else {
            true
        }
    }

    fun requestOverlayDisplayPermission(context: Context) {
        val intent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${context.applicationInfo.packageName}")
                )
            } else {
                null
            }
        if (intent != null) {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setTitle("Screen Overlay Permission Needed")
            builder.setMessage("Enable 'Display over other apps' from System Settings.")
            builder.setPositiveButton("Open Settings") { _, _ ->
                context.startService(intent)
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreate() {
        super.onCreate()
        isViewInitialized = false
        CommanderManager.initialize()
        CommanderManager.getInstance()
            .register(UID, object : Commander.Listener {
                override fun receiveCommand(command: Command) {
                    if (VLogXService.application == null) {
                        return
                    }
                    if (checkOverlayDisplayPermission(VLogXService.application!!)) {
                        if (command.command == COMMAND) {
                            if (isViewInitialized) {
                                try {
                                    if (!adapter.isEmpty()) {
                                        adapter.addData(command.params.toString())
                                    } else {
                                        initVLogX(command)
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    initVLogX(command)
                                }
                            } else {
                                initVLogX(command)
                            }
                        }
                    } else {
                        try {
                            requestOverlayDisplayPermission(VLogXService.application!!)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            })
    }

    private fun initVLogX(command: Command) {
        initializeView()
        adapter.addData(command.params.toString())
    }

    @SuppressLint("InflateParams")
    private fun initializeView() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val metrics = applicationContext.resources.displayMetrics
                val width = metrics.widthPixels
                val height = metrics.heightPixels
                // set the layout parameters of the window
                params = WindowManager.LayoutParams(
                    (width * .9f).toInt(),
                    (height * 0.2f).toInt(),  // Display it on top of other application windows
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // Don't let it grab the input focus
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  // Make the underlying application window visible
                    // through any transparent parts
                    PixelFormat.TRANSLUCENT
                )
            }

            layoutInflater =
                applicationContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater?.inflate(R.layout.floating_view_layout, null)
            view!!.findViewById<AppCompatImageView>(R.id.closeWindowButton).setOnClickListener {
                close()
            }
            params!!.gravity = Gravity.TOP
            params!!.verticalMargin = 0.25f
            params?.x = 0
            params?.y = 0
            windowManager = applicationContext!!.getSystemService(WINDOW_SERVICE) as WindowManager?
            configTouchListener(view)

            // setup adapter
            view!!.findViewById<RecyclerView>(R.id.loggerRecyclerView).adapter = adapter
            open()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun configTouchListener(view: View?) {
        view?.setOnTouchListener(object : View.OnTouchListener {
            val floatWindowLayoutUpdateParam: WindowManager.LayoutParams = params!!
            var x = 0.0
            var y = 0.0
            var px = 0.0
            var py = 0.0

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        x = floatWindowLayoutUpdateParam.x.toDouble()
                        y = floatWindowLayoutUpdateParam.y.toDouble()

                        // returns the original raw X
                        // coordinate of this event
                        px = event.rawX.toDouble()

                        // returns the original raw Y
                        // coordinate of this event
                        py = event.rawY.toDouble()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        floatWindowLayoutUpdateParam.x = (x + event.rawX - px).toInt()
                        floatWindowLayoutUpdateParam.y = (y + event.rawY - py).toInt()

                        // updated parameter is applied to the WindowManager
                        windowManager!!.updateViewLayout(view, floatWindowLayoutUpdateParam)
                    }
                }
                return false
            }
        })
    }


    private fun open() {
        try {
            // check if the view is already
            // inflated or present in the window
            if (view!!.windowToken == null) {
                if (view!!.parent == null) {
                    windowManager!!.addView(view, params)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun close() {
        try {
            // remove the view from the window
            (applicationContext.getSystemService(WINDOW_SERVICE) as WindowManager).removeView(view)
            // invalidate the view
            view!!.invalidate()
            // remove all views
            (view!!.parent as ViewGroup).removeAllViews()

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        VLogXService.application = null
        isViewInitialized = false
        try {
            CommanderManager.getInstance().unregister(UID)
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}