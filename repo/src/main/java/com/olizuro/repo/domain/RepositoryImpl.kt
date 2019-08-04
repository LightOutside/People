package com.olizuro.repo.domain

import com.olizuro.repo.data.ILocalDataSource
import com.olizuro.repo.data.INetworkDataSource
import io.reactivex.processors.BehaviorProcessor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import o.lizuro.core.repo.Contact
import o.lizuro.core.tools.IPreferences
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val preferences: IPreferences,
    private val localDataSource: ILocalDataSource,
    private val networkDataSource: INetworkDataSource
) : IRepository {

    companion object {
        private const val PREFERENCE_KEY_DATA_UPDATE_TIMESTAMP =
            "com.olizuro.repo.domain.PREFERENCE_KEY_DATA_UPDATE_TIMESTAMP"
    }

    override val contacts: BehaviorProcessor<List<Contact>> = BehaviorProcessor.createDefault(listOf())

    override fun loadContacts() {
        val lastDataUpdateTimestamp = preferences.loadLong(PREFERENCE_KEY_DATA_UPDATE_TIMESTAMP, 0L)

        if (lastDataUpdateTimestamp == 0L || (System.currentTimeMillis() - lastDataUpdateTimestamp) / (60 * 1000) >= 1) {

            GlobalScope.launch {
                //TODO background
                val contactsFromGithub = networkDataSource.getContacts()

                //preferences.saveLong(PREFERENCE_KEY_DATA_UPDATE_TIMESTAMP, System.currentTimeMillis())
                //TODO Save to db

                contacts.onNext(listOf())
            }


        } else {
            //TODO background
            contacts.onNext(localDataSource.getContacts())
        }
    }
}