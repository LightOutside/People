package o.lizuro.core.repo

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState

interface IRepoUseCases {
    fun loadContacts(forceRefresh: Boolean)
    fun getContacts() : Flowable<List<Contact>>
    fun getContactsState(): Flowable<ContactsState>
}