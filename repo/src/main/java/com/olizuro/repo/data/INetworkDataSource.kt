package com.olizuro.repo.data

import o.lizuro.core.repo.Contact

interface INetworkDataSource {
    suspend fun getContacts() : String
}