package o.lizuro.core.di

import androidx.lifecycle.ViewModelProvider
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.repo.IRepoUseCases
import o.lizuro.core.tools.*

interface IApplicationProvider : IToolsProvider, IContactsProvider, IRepoProvider, IViewModelFactoryProvider

interface IToolsProvider {
    fun provideApp(): IApp
    fun provideLogger(): ILogger
    fun provideToaster(): IToaster
    fun providePreferences(): IPreferences
    fun provideErrorHandler(): IErrorHandler
    fun provideNetworkChecker(): INetworkChecker
}

interface IContactsProvider {
    fun provideContactUseCases(): IContactsUseCases
}

interface IRepoProvider {
    fun provideRepoUseCases(): IRepoUseCases
}

interface IViewModelFactoryProvider {
    fun provideViewModelsFactory(): ViewModelProvider.Factory
}