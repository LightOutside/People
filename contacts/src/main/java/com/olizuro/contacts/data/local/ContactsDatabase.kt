package com.olizuro.contacts.data.local

import androidx.room.*
import io.reactivex.Flowable
import o.lizuro.core.entities.Contact

@Database(entities = [ContactDb::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun dao(): ContactsDao
}

@Entity
data class ContactDb(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "height")
    val height: Float,
    @ColumnInfo(name = "biography")
    val biography: String,
    @ColumnInfo(name = "temperament")
    val temperament: String,
    @ColumnInfo(name = "education_start")
    val educationStart: String,
    @ColumnInfo(name = "education_end")
    val educationEnd: String
)

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setContacts(contacts: List<ContactDb>)

    @Query("DELETE FROM contactdb")
    fun dropContacts()

    @Query("SELECT * FROM contactdb WHERE id = :id")
    fun getContact(id: String): Flowable<ContactDb>

    @Query("SELECT * FROM contactdb WHERE name LIKE :pattern OR phone LIKE :pattern")
    fun findContacts(pattern: String): Flowable<List<ContactDb>>
}

fun Contact.toContactDb(): ContactDb {
    return ContactDb(
        this.id,
        this.name,
        this.phone,
        this.height,
        this.biography,
        this.temperament.value,
        this.educationPeriod.start,
        this.educationPeriod.end
    )
}

fun ContactDb.toContact(): Contact {
    return Contact(
        this.id,
        this.name,
        this.phone,
        this.height,
        this.biography,
        Contact.Temperament.values().find { it.value == this.temperament }
            ?: throw Exception("Unknown 'temperament', probably database is corrupted"),
        Contact.EducationPeriod(this.educationStart, this.educationEnd)
    )
}