package com.olizuro.contacts.di

import dagger.Component
import o.lizuro.core.di.IContactsProvider
import o.lizuro.core.di.IToolsProvider

@Component(
    dependencies = [IToolsProvider::class],
    modules = [ContactsModule::class]
)
interface ContactsComponent : IContactsProvider {
    class Initializer private constructor() {
        companion object {
            fun init(toolsProvider: IToolsProvider): IContactsProvider {
                return DaggerContactsComponent
                    .builder()
                    .iToolsProvider(toolsProvider)
                    .build()
            }
        }
    }
}