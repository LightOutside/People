package o.lizuro.core.contacts

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState

interface IContactsUseCases {
    fun loadContacts(forceRefresh: Boolean)

    fun getDataState(): Flowable<DataState>
    fun getContact(id: String): Flowable<Contact>
    fun findContacts(pattern: String) : Flowable<List<Contact>>
}