package o.lizuro.core.repo

import io.reactivex.Flowable

interface IRepoUseCases {
    fun loadContacts()
    fun getContacts() : Flowable<List<Contact>>
}