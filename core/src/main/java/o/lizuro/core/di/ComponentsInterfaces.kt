package o.lizuro.core.di

import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactsNavigator
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.repository.IRepository
import o.lizuro.core.tools.ILogger
import o.lizuro.core.tools.IToaster

interface IApplicationProvider : IToolsProvider, IContactsProvider, IRepositoryProvider

interface IToolsProvider {
    fun provideApp(): IApp
    fun provideLogger(): ILogger
    fun provideToast(): IToaster
}

interface IContactsProvider {
    fun provideContactListNavigator(): IContactsNavigator
    fun provideContactListUseCases(): IContactsUseCases
}

interface IRepositoryProvider {
    fun provideRepo(): IRepository
}