package o.lizuro.people.di

import dagger.BindsInstance
import dagger.Component
import o.lizuro.core.IApp
import o.lizuro.core.di.IToolsProvider
import javax.inject.Singleton

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