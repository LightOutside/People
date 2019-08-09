package com.olizuro.repo.di

import dagger.Component
import o.lizuro.core.di.IRepoProvider
import o.lizuro.core.di.IToolsProvider
import javax.inject.Singleton

@Singleton
@Component(
        dependencies = [IToolsProvider::class],
        modules = [RepoModule::class])
interface RepoComponent : IRepoProvider {
    class Initializer private constructor() {
        companion object {
            fun init(toolsProvider: IToolsProvider): IRepoProvider {
                return DaggerRepoComponent.builder()
                        .iToolsProvider(toolsProvider)
                        .build()
            }
        }
    }
}