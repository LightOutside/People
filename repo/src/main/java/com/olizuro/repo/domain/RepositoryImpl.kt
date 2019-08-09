package com.olizuro.repo.domain

import com.olizuro.repo.data.ILocalDataSource
import com.olizuro.repo.data.INetworkDataSource
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.core.tools.INetworkChecker
import o.lizuro.core.tools.IPreferences
import o.lizuro.utils.data.TrieBuilder
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val preferences: IPreferences,
    private val networkChecker: INetworkChecker,
    private val errorHandler: IErrorHandler,
    private val localDataSource: ILocalDataSource,
    private val networkDataSource: INetworkDataSource
) : IRepository {

    companion object {
        private const val PREFERENCE_KEY_DATA_TIMESTAMP =
            "com.olizuro.repo.domain.PREFERENCE_KEY_DATA_TIMESTAMP"

        private const val DATA_TTL = 60 * 1000 //1 min
    }

    private var contactsMap = HashMap<String, Contact>()
    private var contactsTrie = TrieBuilder.empty<String>()
    private var prefix = ""

    override val contacts: BehaviorProcessor<List<String>> = BehaviorProcessor.createDefault(listOf())
    override val contactsState: BehaviorProcessor<ContactsState> = BehaviorProcessor.createDefault(ContactsState.LOADING)


    override fun loadContacts(forceRefresh: Boolean) {
        dropContacts()

        contactsState.onNext(ContactsState.LOADING)

        GlobalScope.launch {
            val currentTime = System.currentTimeMillis()
            val dataTimestamp = preferences.loadLong(PREFERENCE_KEY_DATA_TIMESTAMP, 0L)

            if (currentTime - dataTimestamp > DATA_TTL || forceRefresh) {

                if(networkChecker.isOnline()) {
                    val contactsFromGithub = networkDataSource.getContacts()

                    preferences.saveLong(PREFERENCE_KEY_DATA_TIMESTAMP, System.currentTimeMillis())
                    localDataSource.setContacts(contactsFromGithub)

                    setupContacts(contactsFromGithub)

                } else {
                    errorHandler.notifyError("Нет подключения к сети")
                }
            } else {
                val contactsFromDatabase = localDataSource.getContacts()
                setupContacts(contactsFromDatabase)
            }
            contactsState.onNext(ContactsState.LOADED)
        }
    }

    @Throws(Exception::class)
    override fun getContact(id: String): Contact {
        return contactsMap[id] ?: throw Exception("Can't find Contact with id: $id")
    }

    override fun setContactsPrefix(prefix: String) {
        this.prefix = prefix
        val filteredContacts = if (prefix.isEmpty()) contactsMap.keys.toList() else contactsTrie.get(prefix, true)
        contacts.onNext(filteredContacts)
    }

    private fun setupContacts(contactsList: List<Contact>) {
        contactsList.forEach {
            contactsMap[it.id] = it
            contactsTrie.add(it.name.toLowerCase(), it.id)
            val phonePlain = Regex("[^0-9]").replace(it.phone, "")
            contactsTrie.add(phonePlain, it.id)
        }

        val filteredContacts = if (prefix.isEmpty()) contactsMap.keys.toList() else contactsTrie.get(prefix, true)
        contacts.onNext(filteredContacts)
    }

    private fun dropContacts() {
        contactsTrie = TrieBuilder.empty()
        contactsMap.clear()
    }
}