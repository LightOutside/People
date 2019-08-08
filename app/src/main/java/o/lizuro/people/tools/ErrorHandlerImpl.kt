package o.lizuro.people.tools

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import o.lizuro.core.tools.IErrorHandler

class ErrorHandlerImpl : IErrorHandler {

    private var errorMessagesProcessor = PublishProcessor.create<String>()

    override fun getErrorMessages(): Flowable<String> {
        return errorMessagesProcessor
    }

    override fun handleError(th: Throwable) {
        errorMessagesProcessor.
        //TODO Some logic for correct exceptions handling
    }
}