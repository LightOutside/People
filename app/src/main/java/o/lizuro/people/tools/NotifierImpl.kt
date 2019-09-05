package o.lizuro.people.tools

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import o.lizuro.core.tools.INotifier
import javax.inject.Inject

class NotifierImpl @Inject constructor() : INotifier {

    private val messagesProcessor = PublishProcessor.create<String>()

    override val messagesFlow: Flowable<String>
        get() {
            return messagesProcessor
        }

    override fun notify(message: String) {
        messagesProcessor.onNext(message)
    }
}