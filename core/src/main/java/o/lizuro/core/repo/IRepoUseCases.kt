package o.lizuro.core.repo

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState

interface IRepoUseCases {
    fun loadContacts(forceRefresh: Boolean)
    fun setContactsPrefix(prefix: String)

    fun getContact(id: String) : Contact
    fun getContacts() : Flowable<List<String>>

    fun getContactsState(): Flowable<ContactsState>
}