package com.olizuro.repo.data

import o.lizuro.core.entities.Contact

interface INetworkDataSource {
    suspend fun getContacts() : List<Contact>
}