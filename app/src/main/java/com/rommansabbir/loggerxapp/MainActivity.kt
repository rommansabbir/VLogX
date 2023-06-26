package com.rommansabbir.loggerxapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rommansabbir.vlogx.ext.clearAndCloseLog
import com.rommansabbir.vlogx.ext.clearLog
import com.rommansabbir.vlogx.ext.sendLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class MainActivity : AppCompatActivity() {
    companion object {
        private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"
    }

    private fun getRandomString(sizeOfRandomString: Int): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            repeat(500) {
                delay(2000)
                when {
                    (it == 5 || it == 15 || it == 25 || it == 35) -> {
                        delay(2000)
                        sendLog("Clearing Logs")
                        delay(2000)
                        clearLog()
                    }

                    (it == 10 || it == 20 || it == 30 || it == 40) -> {
                        delay(2000)
                        sendLog("Clearing Logs And Close the Logger View")
                        delay(2000)
                        clearAndCloseLog()
                    }

                    else -> {
                        sendLog("$it: ${getRandomString(100)}")
                    }
                }
            }
        }
    }
}