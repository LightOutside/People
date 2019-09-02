package com.olizuro.contacts.data.local

import androidx.room.*
import io.reactivex.Flowable
import o.lizuro.core.IApp
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.Contact.EducationPeriod
import o.lizuro.core.entities.Contact.Temperament
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    app: IApp
) : ILocalDataSource {

    private val db: ContactsDatabase =
        Room.databaseBuilder(app.getApplicationContext(), ContactsDatabase::class.java, "People").build()

    override fun getContact(id: String): Flowable<Contact> {
        return db.dao().getContact(id).distinctUntilChanged().map { it.toContact() }
    }

    override fun setContacts(contacts: List<Contact>) {
        db.dao().dropContacts()
        db.dao().setContacts(contacts.map { it.toContactDb() })
    }

    override fun findContacts(pattern: String): Flowable<List<Contact>> {
        return db.dao().findContacts("%$pattern%").distinctUntilChanged().map { list -> list.map { it.toContact() } }
    }
}