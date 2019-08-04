package com.olizuro.contacts.di

import com.olizuro.contacts.api.ContactsApiImpl
import dagger.Binds
import dagger.Module
import o.lizuro.core.contacts.IContactsApi

@Module
interface ContactsApiModule {
    @Binds
    fun bindsContactsApi(impl: ContactsApiImpl): IContactsApi
}
