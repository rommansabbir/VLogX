package com.rommansabbir.vlogx.ext

import com.google.gson.Gson
import com.rommansabbir.commander.Command
import com.rommansabbir.commander.CommanderManager
import com.rommansabbir.vlogx.core.VLogXService

val gson by lazy { Gson() }

fun sendLog(log: Any) {
    CommanderManager.getInstance()
        .broadcastCommand(Command(VLogXService.COMMAND, gson.toJson(log), VLogXService.UID))
}