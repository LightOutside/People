package o.lizuro.core.tools

import io.reactivex.Flowable

interface INotifier {
    val messagesFlow : Flowable<String>
    fun notify(message: String)
}