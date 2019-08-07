package com.olizuro.repo.data

import androidx.room.*
import o.lizuro.core.IApp
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.EducationPeriod
import o.lizuro.core.entities.Temperament
import java.lang.Exception
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    app: IApp
) : ILocalDataSource {
    private val db : AppDatabase = Room.databaseBuilder(app.getApplicationContext(), AppDatabase::class.java, "People").build()

    override fun setContacts(contacts: List<Contact>) {
        db.dao().dropContacts()
        db.dao().setContacts(contacts.map { it.toContactDb() })
    }

    override fun getContacts(): List<Contact> {
        return db.dao().getContacts().map { it.toContact() }
    }
}

@Database(entities = [ContactDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): ContactsDao
}

@Entity
data class ContactDb(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "height") val height: Float,
    @ColumnInfo(name = "biography") val biography: String,
    @ColumnInfo(name = "temperament") val temperament: String,
    @ColumnInfo(name = "education_start") val educationStart: String,
    @ColumnInfo(name = "education_end") val educationEnd: String
)

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contactdb")
    fun getContacts(): List<ContactDb>

    @Insert
    fun setContacts(contacts: List<ContactDb>)

    @Query("DELETE FROM contactdb")
    fun dropContacts()
}

fun Contact.toContactDb() : ContactDb {
    return ContactDb(
        this.id,
        this.name,
        this.height,
        this.biography,
        this.temperament.value,
        this.educationPeriod.start,
        this.educationPeriod.end
    )
}

fun ContactDb.toContact() : Contact {
    return Contact(
        this.id,
        this.name,
        this.height,
        this.biography,
        Temperament.values().find { it.value == this.temperament } ?: throw Exception("Unknown 'temperament', probably database is corrupted"),
        EducationPeriod(this.educationStart, this.educationEnd)
    )
}