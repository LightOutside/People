package o.lizuro.core.di

import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactsApi
import o.lizuro.core.repo.IRepoUseCases
import o.lizuro.core.tools.ILogger
import o.lizuro.core.tools.IPreferences
import o.lizuro.core.tools.IToaster

interface IApplicationProvider : IToolsProvider, IContactsProvider, IRepoProvider

interface IToolsProvider {
    fun provideApp(): IApp
    fun provideLogger(): ILogger
    fun provideToaster(): IToaster
    fun providePreferences(): IPreferences
}

interface IContactsProvider {
    fun provideContactApi(): IContactsApi
}

interface IRepoProvider {
    fun provideRepo(): IRepoUseCases
}