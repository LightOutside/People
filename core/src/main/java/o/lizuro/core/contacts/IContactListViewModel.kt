package o.lizuro.core.contacts

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState

interface IContactListViewModel {
    fun getContacts() : Flowable<List<Contact>>
    fun getContactsState() : Flowable<ContactsState>

    fun inputTextChanged(text: String)
    fun contactSelected(contactId: String)
    fun pullToRefresh()
}