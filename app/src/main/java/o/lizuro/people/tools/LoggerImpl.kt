package o.lizuro.people.tools

import android.util.Log
import o.lizuro.core.tools.ILogger
import javax.inject.Inject

class LoggerImpl @Inject constructor() : ILogger {
    override fun d(message: String?) {
        Log.d("[People]", message)
    }
}