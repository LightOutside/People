package com.olizuro.contacts.di

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.presentation.views.list.ContactListFragment
import com.olizuro.contacts.presentation.viewmodels.list.ContactListViewModel
import dagger.Module
import dagger.Provides
import com.olizuro.contacts.presentation.viewmodels.list.IContactListViewModel
import o.lizuro.utils.di.general.ViewModelFactory

@Module
class ContactListFragmentModule {
    @Provides
    fun providesIContactListViewModel(
        fragment: ContactListFragment,
        factory: ViewModelFactory<ContactListViewModel>
    ): IContactListViewModel {
        return ViewModelProviders.of(fragment, factory).get(ContactListViewModel::class.java)
    }

    @Provides
    fun provideFragmentArguments(fragment: ContactListFragment): Bundle = fragment.arguments ?: Bundle()
}