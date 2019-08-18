package o.lizuro.core.contacts

import android.content.Context
import io.reactivex.Flowable
import o.lizuro.core.entities.Contact

interface IContactInfoViewModel {
    fun getContact(id: String) : Flowable<Contact>
    fun showDialer(context: Context, phone: String)
}