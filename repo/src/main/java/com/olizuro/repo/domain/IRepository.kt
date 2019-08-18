package com.olizuro.repo.domain

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import java.util.regex.Pattern

interface IRepository {
    val contacts: Flowable<List<Contact>>
    val dataState: Flowable<DataState>

    fun loadContacts(forceRefresh: Boolean)
    fun findContacts(pattern: String)

    fun getContact(id: String) : Flowable<Contact>
}