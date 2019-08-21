package com.olizuro.contacts.domain

import io.reactivex.Flowable
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import javax.inject.Inject

class ContactsUseCasesImpl @Inject constructor(
    private val repository: IRepository
) : IContactsUseCases {

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