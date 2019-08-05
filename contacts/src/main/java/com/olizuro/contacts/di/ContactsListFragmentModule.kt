package com.olizuro.contacts.di

import com.olizuro.contacts.presentation.ContactsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ContactsListFragmentModule {

    @ContributesAndroidInjector
    fun contributeContactsListFragment(): ContactsListFragment

//    @FragmentScope
    //TODO Bind vm
}