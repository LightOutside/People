package o.lizuro.core.di

import o.lizuro.core.contacts.IContactsUseCases

interface IContactsProvider {
    fun provideContactUseCases(): IContactsUseCases
}