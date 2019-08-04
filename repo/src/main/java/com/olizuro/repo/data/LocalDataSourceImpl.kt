package com.olizuro.repo.data

import o.lizuro.core.repo.Contact
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor() : ILocalDataSource {
    override fun getContacts(): List<Contact> {
        return listOf()
    }
}