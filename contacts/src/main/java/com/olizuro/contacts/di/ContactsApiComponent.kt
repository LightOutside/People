package com.olizuro.contacts.di

import dagger.Component
import o.lizuro.core.di.IContactsProvider

@Component(
    modules = [ContactsApiModule::class]
)
interface ContactsApiComponent : IContactsProvider {
    class Initializer private constructor() {
        companion object {
            fun init(): IContactsProvider {
                return DaggerContactsApiComponent.builder().build()
            }
        }
    }
}