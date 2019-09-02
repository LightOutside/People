package o.lizuro.core.di

import o.lizuro.core.IApp
import o.lizuro.core.tools.*

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