package com.olizuro.contacts.di

import com.olizuro.contacts.presentation.ContactsListFragment
import dagger.Component
import o.lizuro.core.di.IApplicationProvider
import o.lizuro.utils.di.general.FragmentScope

@FragmentScope
@Component(
    dependencies = [IApplicationProvider::class],
    modules = [ContactsListFragmentModule::class]
)
interface ContactsListFragmentComponent {
    fun inject(fragment: ContactsListFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                applicationProvider: IApplicationProvider
            ): ContactsListFragmentComponent {
                return DaggerContactsListFragmentComponent.builder()
                    .iApplicationProvider(applicationProvider)
                    .build()
            }
        }
    }
}