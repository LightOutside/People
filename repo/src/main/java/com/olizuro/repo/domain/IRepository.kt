package com.olizuro.repo.domain

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState

interface IRepository {
    val contacts: Flowable<List<String>>
    val contactsState: Flowable<ContactsState>

    fun loadContacts(forceRefresh: Boolean)
    fun setContactsPrefix(prefix: String)

    fun getContact(id: String) : Contact
}