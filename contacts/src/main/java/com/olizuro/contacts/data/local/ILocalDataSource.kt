package com.olizuro.contacts.data.local

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact

interface ILocalDataSource {
    fun setContacts(contacts: List<Contact>)

    fun getContact(id: String) : Flowable<Contact>
    fun findContacts(pattern: String): Flowable<List<Contact>>
}