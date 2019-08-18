package o.lizuro.core.repo

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState

interface IRepoUseCases {
    fun loadContacts(forceRefresh: Boolean)
    fun findContacts(pattern: String)

    fun getContact(id: String): Flowable<Contact>
    fun getContacts(): Flowable<List<Contact>>
    fun getDataState(): Flowable<DataState>
}