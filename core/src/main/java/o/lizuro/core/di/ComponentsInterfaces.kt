package o.lizuro.core.di

import androidx.lifecycle.ViewModelProvider
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.repo.IRepoUseCases
import o.lizuro.core.tools.ILogger
import o.lizuro.core.tools.IPreferences
import o.lizuro.core.tools.IToaster

interface IApplicationProvider : IToolsProvider, IContactsProvider, IRepoProvider, IViewModelsProvider

interface IToolsProvider {
    fun provideApp(): IApp
    fun provideLogger(): ILogger
    fun provideToaster(): IToaster
    fun providePreferences(): IPreferences
}

interface IContactsProvider {
    fun provideContactUseCases(): IContactsUseCases
}

interface IRepoProvider {
    fun provideRepoUseCases(): IRepoUseCases
}

interface IViewModelsProvider {
    fun provideViewModelsFactory(): ViewModelProvider.Factory
    //fun provideContactListViewModel(): IContactListViewModel
}