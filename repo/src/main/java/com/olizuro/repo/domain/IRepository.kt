package com.olizuro.repo.domain

import io.reactivex.Flowable
import io.reactivex.Maybe
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import org.intellij.lang.annotations.Flow
import java.util.regex.Pattern

interface IRepository {
    fun loadContacts(forceRefresh: Boolean)

    fun getDataState(): Flowable<DataState>
    fun getContact(id: String) : Flowable<Contact>
    fun findContacts(pattern: String): Flowable<List<Contact>>
}