package com.olizuro.contacts.data

import o.lizuro.core.entities.Contact

interface INetworkDataSource {
    suspend fun getContacts() : List<Contact>
}