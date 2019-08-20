package com.olizuro.contacts.di

import com.olizuro.contacts.domain.ContactsUseCasesImpl
import dagger.Binds
import dagger.Module
import o.lizuro.core.contacts.IContactsUseCases
import javax.inject.Singleton

@Module
interface ContactsModule {
    @Binds
    fun bindsContactsApi(impl: ContactsUseCasesImpl): IContactsUseCases
    @Binds
    fun bindsRepository(impl: com.olizuro.contacts.domain.RepositoryImpl): com.olizuro.contacts.domain.IRepository
    @Binds
    fun bindsNetworkDataSource(impl: com.olizuro.contacts.data.NetworkDataSourceImpl): com.olizuro.contacts.data.INetworkDataSource
    @Binds
    fun bindsLocalDataSource(impl: com.olizuro.contacts.data.LocalDataSourceImpl): com.olizuro.contacts.data.ILocalDataSource
}
