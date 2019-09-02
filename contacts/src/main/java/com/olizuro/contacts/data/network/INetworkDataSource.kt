package com.olizuro.contacts.data.network

import o.lizuro.core.entities.Contact

interface INetworkDataSource {
    suspend fun getContacts() : List<Contact>
}