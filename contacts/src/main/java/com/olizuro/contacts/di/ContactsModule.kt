package com.olizuro.contacts.di

import com.olizuro.contacts.data.local.ILocalDataSource
import com.olizuro.contacts.data.local.LocalDataSourceImpl
import com.olizuro.contacts.data.network.INetworkDataSource
import com.olizuro.contacts.data.network.NetworkDataSourceImpl
import com.olizuro.contacts.domain.ContactsUseCasesImpl
import dagger.Binds
import dagger.Module
import o.lizuro.core.contacts.IContactsUseCases

@Module
interface ContactsModule {
    @Binds
    fun bindsContactsApi(impl: ContactsUseCasesImpl): IContactsUseCases
    @Binds
    fun bindsRepository(impl: com.olizuro.contacts.domain.RepositoryImpl): com.olizuro.contacts.domain.IRepository
    @Binds
    fun bindsNetworkDataSource(impl: NetworkDataSourceImpl): INetworkDataSource
    @Binds
    fun bindsLocalDataSource(impl: LocalDataSourceImpl): ILocalDataSource
}
