package com.olizuro.contacts.di

import dagger.Component
import o.lizuro.core.di.IContactsProvider

@Component(
    modules = [ContactsUseCasesModule::class]
)
interface ContactsUseCasesComponent : IContactsProvider {
    class Initializer private constructor() {
        companion object {
            fun init(): IContactsProvider {
                return DaggerContactsUseCasesComponent.builder().build()
            }
        }
    }
}