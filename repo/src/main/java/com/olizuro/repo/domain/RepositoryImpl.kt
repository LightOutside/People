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

    override val contacts: BehaviorProcessor<List<Contact>> = BehaviorProcessor.createDefault(listOf())

    override val contactsState: BehaviorProcessor<ContactsState> = BehaviorProcessor.createDefault(ContactsState.LOADING)

    override fun loadContacts(forceRefresh: Boolean) {
        contactsState.onNext(ContactsState.LOADING)

        GlobalScope.launch {
            val currentTime = System.currentTimeMillis()
            val dataTimestamp = preferences.loadLong(PREFERENCE_KEY_DATA_TIMESTAMP, 0L)

            if (currentTime - dataTimestamp > DATA_TTL || forceRefresh) {
                if(networkChecker.isOnline()) {
                    val contactsFromGithub = networkDataSource.getContacts().sortedBy { it.name }

                    preferences.saveLong(PREFERENCE_KEY_DATA_TIMESTAMP, System.currentTimeMillis())
                    localDataSource.setContacts(contactsFromGithub)

                    contacts.onNext(contactsFromGithub)
                } else {
                    errorHandler.notifyError("Нет подключения к сети")
                }
            } else {
                val contactsFromDatabase = localDataSource.getContacts()
                contacts.onNext(contactsFromDatabase)
            }

            contactsState.onNext(ContactsState.LOADED)
        }
    }
}