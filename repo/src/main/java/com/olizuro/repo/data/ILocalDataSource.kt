package com.olizuro.repo.data

import o.lizuro.core.entities.Contact

interface ILocalDataSource {
    fun getContacts(): List<Contact>
    fun setContacts(contacts: List<Contact>)
}