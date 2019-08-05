package com.olizuro.contacts.di

import com.olizuro.contacts.api.ContactsUseCasesImpl
import dagger.Binds
import dagger.Module
import o.lizuro.core.contacts.IContactsUseCases

@Module
interface ContactsUseCasesModule {
    @Binds
    fun bindsContactsApi(impl: ContactsUseCasesImpl): IContactsUseCases
}
