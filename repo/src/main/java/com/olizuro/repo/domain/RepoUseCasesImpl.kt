package com.olizuro.repo.domain

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState
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

    override fun setContactsPrefix(prefix: String) {
        repository.setContactsPrefix(prefix)
    }

    override fun getContactsState(): Flowable<ContactsState> {
        return repository.contactsState
    }

    override fun getContact(id: String): Contact {
        return repository.getContact(id)
    }

    override fun getContacts(): Flowable<List<String>> {
        return repository.contacts
    }
}