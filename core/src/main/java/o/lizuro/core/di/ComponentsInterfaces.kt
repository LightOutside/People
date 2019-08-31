package o.lizuro.core.di

import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.mainscreen.IMainScreenUseCases
import o.lizuro.core.tools.*

interface IApplicationProvider : IToolsProvider, IContactsProvider, IMainScreenProvider

interface IToolsProvider {
    fun provideApp(): IApp
    fun provideLogger(): ILogger
    fun provideToaster(): IToaster
    fun providePreferences(): IPreferences
    fun provideErrorHandler(): INotifier
    fun provideNetworkChecker(): INetworkChecker
    fun provideNavigator(): INavigation
    fun provideSystemNavigator(): ISystemNavigator
}

interface IContactsProvider {
    fun provideContactUseCases(): IContactsUseCases
}

interface IMainScreenProvider {
    fun provideMainScreenUseCases(): IMainScreenUseCases
}