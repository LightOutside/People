package o.lizuro.people.di

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import o.lizuro.core.IApp
import o.lizuro.core.di.IToolsProvider
import o.lizuro.core.tools.ILogger
import o.lizuro.core.tools.IToaster
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
    }
}

@Singleton
@Component(modules = [ToolsModule::class])
interface ToolsComponent : IToolsProvider {

    @Component.Builder
    interface Builder {
        fun build(): ToolsComponent
        @BindsInstance
        fun app(app: IApp): Builder
    }

    class Initializer private constructor() {
        companion object {
            fun init(app: IApp): IToolsProvider =
                DaggerToolsComponent.builder()
                    .app(app)
                    .build()
        }
    }

}