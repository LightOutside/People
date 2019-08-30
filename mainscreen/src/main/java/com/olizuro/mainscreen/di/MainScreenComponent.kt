package com.olizuro.mainscreen.di

import dagger.Component
import o.lizuro.core.di.IMainScreenProvider
import o.lizuro.core.di.IToolsProvider
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [IToolsProvider::class],
    modules = [MainScreenModule::class]
)
interface MainScreenComponent : IMainScreenProvider {
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