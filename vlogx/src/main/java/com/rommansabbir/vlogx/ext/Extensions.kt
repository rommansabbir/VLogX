package com.rommansabbir.vlogx.ext

import com.rommansabbir.commander.Command
import com.rommansabbir.commander.CommanderManager
import com.rommansabbir.vlogx.core.VLogXService

/**
 * Send a log to the core service.
 *
 * @param log [Any] data to be shown to the log UI.
 *
 * @return [Unit]
 */
fun sendLog(log: Any) {
    CommanderManager.initialize()
    CommanderManager.getInstance()
        .broadcastCommand(Command(VLogXService.NEW_LOG_COMMAND, log.toString(), VLogXService.UID))
}

/**
 * Clear list of logs.
 *
 * call this function for clear data is showing in the log UI.
 *
 * @return [Unit]
 */
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

/**
 * Clear list of logs and close the vlogx view.
 *
 * call this function for clear data is showing in the log UI and close he vlogx view.
 *
 * @return [Unit]
 */
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