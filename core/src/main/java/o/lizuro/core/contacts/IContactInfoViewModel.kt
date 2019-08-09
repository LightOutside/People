package o.lizuro.core.contacts

import android.content.Context
import io.reactivex.Flowable
import o.lizuro.core.entities.Contact

interface IContactInfoViewModel {
    fun getContact() : Flowable<Contact>

    fun setContactId(contactId: String)
    fun showDialer(context: Context)
}