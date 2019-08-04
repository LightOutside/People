package com.olizuro.contacts.di

import com.olizuro.contacts.presentation.ContactsListFragment
import dagger.Component
import o.lizuro.core.di.IContactsProvider
import o.lizuro.core.di.IRepoProvider
import o.lizuro.utils.di.general.FragmentScope

@FragmentScope
@Component(
    dependencies = [IRepoProvider::class, IContactsProvider::class],
    modules = [ContactsListFragmentModule::class]
)
interface ContactsListFragmentComponent {
    fun inject(fragment: ContactsListFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                repoProvider: IRepoProvider,
                contactsProvider: IContactsProvider
            ): ContactsListFragmentComponent {
                return DaggerContactsListFragmentComponent.builder()
                    .iRepoProvider(repoProvider)
                    .iContactsProvider(contactsProvider)
                    .build()
            }
        }
    }
}