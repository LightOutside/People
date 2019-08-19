package com.olizuro.repo.domain

import com.olizuro.repo.data.ILocalDataSource
import com.olizuro.repo.data.INetworkDataSource
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
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
        private const val PREFERENCE_KEY_DATA_TIMESTAMP = "com.olizuro.repo.domain.PREFERENCE_KEY_DATA_TIMESTAMP"
        private const val DATA_TTL = 60 * 1000 //1 min
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        errorHandler.handleError(exception)
    }

    private val dataState: BehaviorProcessor<DataState> = BehaviorProcessor.createDefault(DataState.LOADING)

    override fun loadContacts(forceRefresh: Boolean) {
        GlobalScope.launch(exceptionHandler) {

            val currentTime = System.currentTimeMillis()
            val dataTimestamp = preferences.loadLong(PREFERENCE_KEY_DATA_TIMESTAMP, 0L)

            if (currentTime - dataTimestamp > DATA_TTL || forceRefresh) {
                if (networkChecker.isOnline()) {
                    dataState.onNext(DataState.LOADING)

                    val contactsFromGithub = networkDataSource.getContacts()
                    preferences.saveLong(PREFERENCE_KEY_DATA_TIMESTAMP, System.currentTimeMillis())
                    localDataSource.setContacts(contactsFromGithub)

                    dataState.onNext(DataState.LOADED)
                } else {
                    errorHandler.notifyError("Нет подключения к сети")
                }
            }
        }
    }

    override fun getDataState(): Flowable<DataState> {
        return dataState
    }

    override fun getContact(id: String): Flowable<Contact> {
        return localDataSource.getContact(id)
    }

    override fun findContacts(pattern: String): Flowable<List<Contact>> {
        return localDataSource.findContacts(pattern)
    }
}