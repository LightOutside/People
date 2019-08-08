package com.olizuro.repo.domain

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState
import o.lizuro.core.repo.IRepoUseCases
import javax.inject.Inject

class RepoUseCasesImpl @Inject constructor(
    private val repository: IRepository
) : IRepoUseCases {

    override fun loadContacts(forceRefresh: Boolean) {
        repository.loadContacts(forceRefresh)
    }

    override fun getContactsState(): Flowable<ContactsState> {
        return repository.contactsState
    }

    override fun getContacts(): Flowable<List<Contact>> {
        return repository.contacts
    }
}