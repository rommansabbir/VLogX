package com.rommansabbir.loggerxapp

import android.app.Application
import com.rommansabbir.vlogx.core.VLogXService

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        VLogXService.init(this)
    }
}