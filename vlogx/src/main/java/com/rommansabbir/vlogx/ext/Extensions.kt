package com.rommansabbir.vlogx.ext

import com.google.gson.Gson
import com.rommansabbir.commander.Command
import com.rommansabbir.commander.CommanderManager
import com.rommansabbir.vlogx.core.LoggerXService

val gson by lazy { Gson() }

fun sendLog(log: Any) {
    CommanderManager.getInstance()
        .broadcastCommand(Command(LoggerXService.COMMAND, gson.toJson(log), LoggerXService.UID))
}