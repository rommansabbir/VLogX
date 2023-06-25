package com.rommansabbir.vlogx.ext

import com.rommansabbir.commander.Command
import com.rommansabbir.commander.CommanderManager
import com.rommansabbir.vlogx.core.VLogXService

fun sendLog(log: Any) {
    CommanderManager.initialize()
    CommanderManager.getInstance()
        .broadcastCommand(Command(VLogXService.NEW_LOG_COMMAND, log.toString(), VLogXService.UID))
}

fun clearLog() {
    CommanderManager.initialize()
    CommanderManager.getInstance()
        .broadcastCommand(
            Command(
                VLogXService.CLEAR_LOGS_COMMAND,
                VLogXService.CLEAR_LOGS_COMMAND,
                VLogXService.UID
            )
        )
}

fun clearAndCloseLog() {
    CommanderManager.initialize()
    CommanderManager.getInstance()
        .broadcastCommand(
            Command(
                VLogXService.CLEAR_LOGS_AND_CLOSE_COMMAND,
                VLogXService.CLEAR_LOGS_AND_CLOSE_COMMAND,
                VLogXService.UID
            )
        )
}