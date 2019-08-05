package com.olizuro.repo.data

import android.content.Context
import androidx.room.*
import o.lizuro.core.entities.Contact
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    appContext: Context
) : ILocalDataSource {
    private val db : AppDatabase = Room.databaseBuilder(appContext, AppDatabase::class.java, "People").build()

    override fun setContacts(contacts: List<Contact>) {

    }

    override fun getContacts(): List<Contact> {
        return listOf()
    }
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

@Entity
data class User(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "height") val height: Float,
    @ColumnInfo(name = "biography") val biography: String,
    @ColumnInfo(name = "temperament") val temperament: String, //TODO
    @ColumnInfo(name = "education_start") val educationStart: String,
    @ColumnInfo(name = "education_end") val educationEnd: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
//    @Insert
//    fun insertAll(vararg users: User)
//
//    @Delete
//    fun delete(user: User)
}