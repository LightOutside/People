package com.olizuro.repo.domain

import io.reactivex.Flowable
import o.lizuro.core.repo.Contact

interface IRepository {
    val contacts: Flowable<List<Contact>>

    fun loadContacts()
}