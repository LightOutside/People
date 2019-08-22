package com.olizuro.mainscreen.di

import com.olizuro.mainscreen.presentation.views.MainActivity
import dagger.Component
import o.lizuro.core.di.IToolsProvider
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [IToolsProvider::class],
    modules = [MainScreenModule::class]
)
interface MainScreenComponent : IMainScreenProvider {
    fun inject(activity: MainActivity)

    class Initializer private constructor() {
        companion object {
            fun init(toolsProvider: IToolsProvider): IMainScreenProvider {
                return DaggerMainScreenComponent
                    .builder()
                    .iToolsProvider(toolsProvider)
                    .build()
            }
        }
    }
}