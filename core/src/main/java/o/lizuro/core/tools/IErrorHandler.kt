package o.lizuro.core.tools

import io.reactivex.Flowable

interface IErrorHandler {
    fun getErrorMessages() : Flowable<String>
    fun handleError(th: Throwable)
    fun notifyError(message: String)
}