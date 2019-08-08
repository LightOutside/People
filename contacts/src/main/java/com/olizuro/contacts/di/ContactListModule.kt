package com.olizuro.contacts.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.presentation.views.ContactListFragment
import com.olizuro.contacts.presentation.viewmodels.ContactListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import o.lizuro.core.contacts.IContactListViewModel

@Module
abstract class ContactListModule {
    @Module
    companion object {
        @Provides
        fun providesIContactListViewModel(
            fragment: ContactListFragment,
            factory: ViewModelProvider.Factory
        ): ContactListViewModel {
            return ViewModelProviders.of(fragment, factory)[ContactListViewModel::class.java]
        }
    }

    @Binds
    abstract fun bindsIContactListViewModel(viewModel: ContactListViewModel): IContactListViewModel

    @ContributesAndroidInjector
    abstract fun bindsContactListFragment(): ContactListFragment
}