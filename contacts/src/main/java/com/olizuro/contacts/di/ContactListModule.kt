package com.olizuro.contacts.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.presentation.ContactListFragment
import com.olizuro.contacts.presentation.viewmodels.ContactListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.utils.di.annotations.ViewModelKey

@Module
abstract class ContactListModule {
    @Module
    companion object {
        @Provides
        fun providesIContactListViewModel(fragment: ContactListFragment, factory: ViewModelProvider.Factory) : ContactListViewModel {
            return ViewModelProviders.of(fragment, factory)[ContactListViewModel::class.java]
        }
    }

    @Binds
    abstract fun bindsIContactListViewModel(viewModel: ContactListViewModel): IContactListViewModel

    @ContributesAndroidInjector
    abstract fun bindsContactListFragment(): ContactListFragment
}