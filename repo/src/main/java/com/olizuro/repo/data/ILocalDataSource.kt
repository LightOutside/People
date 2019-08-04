package com.olizuro.repo.data

import o.lizuro.core.repo.Contact

interface ILocalDataSource {
    fun getContacts() : List<Contact>
}