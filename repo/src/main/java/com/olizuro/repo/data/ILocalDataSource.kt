package com.olizuro.repo.data

import o.lizuro.core.entities.Contact

interface ILocalDataSource {
    fun getContact(id: String) : Contact
    fun findContacts(pattern: String): List<Contact>
    fun setContacts(contacts: List<Contact>)
}