package o.lizuro.people.di

import dagger.Binds
import dagger.Module
import o.lizuro.core.tools.*
import o.lizuro.people.tools.*
import javax.inject.Singleton

@Module
interface ToolsModule {
    @Binds
    @Singleton
    fun bindsLogger(impl: LoggerImpl): ILogger

    @Binds
    @Singleton
    fun bindsToaster(impl: ToasterImpl): IToaster

    @Binds
    @Singleton
    fun bindsPreferences(impl: PreferencesImpl): IPreferences

    @Binds
    @Singleton
    fun bindsErrorHandler(impl: NotifierImpl): INotifier

    @Binds
    @Singleton
    fun bindsNetworkChecker(impl: NetworkCheckerImpl): INetworkChecker

    @Binds
    @Singleton
    fun bindsNavigation(impl: NavigationImpl): INavigation

    @Binds
    @Singleton
    fun bindsSystemNavigator(impl: SystemNavigatorImpl): ISystemNavigator
}