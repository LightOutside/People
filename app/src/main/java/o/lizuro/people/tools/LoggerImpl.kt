package o.lizuro.people.tools

import android.util.Log
import o.lizuro.core.tools.ILogger

class LoggerImpl : ILogger {
    override fun d(message: String) {
        Log.d("QQQ", message)
    }
}