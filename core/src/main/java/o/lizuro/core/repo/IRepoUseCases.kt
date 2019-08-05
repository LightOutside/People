package o.lizuro.core.repo

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact

interface IRepoUseCases {
    fun loadContacts()
    fun getContacts() : Flowable<List<Contact>>
}