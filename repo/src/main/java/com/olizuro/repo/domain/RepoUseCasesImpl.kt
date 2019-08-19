package com.olizuro.repo.domain

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import o.lizuro.core.repo.IRepoUseCases
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoUseCasesImpl @Inject constructor(
    private val repository: IRepository
) : IRepoUseCases {

    override fun loadContacts(forceRefresh: Boolean) {
        repository.loadContacts(forceRefresh)
    }


    override fun findContacts(pattern: String) : Flowable<List<Contact>> {
        return repository.findContacts(pattern)
    }

    override fun getDataState(): Flowable<DataState> {
        return repository.getDataState()
    }

    override fun getContact(id: String): Flowable<Contact> {
        return repository.getContact(id)
    }
}