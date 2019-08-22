package o.lizuro.people.di

import dagger.Module
import dagger.Provides
import o.lizuro.core.IApp
import o.lizuro.core.tools.*
import o.lizuro.people.tools.*
import javax.inject.Singleton

@Module
class ToolsModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideLogger(): ILogger {
            return LoggerImpl()
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideToaster(app: IApp): IToaster {
            return ToasterImpl(app.getApplicationContext())
        }

        @JvmStatic
        @Provides
        @Singleton
        fun providePreferences(app: IApp): IPreferences {
            return PreferencesImpl(app.getApplicationContext())
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideErrorHandler(): IErrorHandler {
            return ErrorHandlerImpl()
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideNetworkChecker(app: IApp): INetworkChecker {
            return NetworkCheckerImpl(app.getApplicationContext())
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideNavigation(): INavigation {
            return NavigationImpl()
        }
    }
}