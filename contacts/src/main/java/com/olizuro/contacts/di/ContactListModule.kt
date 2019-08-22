package com.olizuro.contacts.di

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.presentation.views.ContactListFragment
import com.olizuro.contacts.presentation.viewmodels.ContactListViewModel
import dagger.Module
import dagger.Provides
import com.olizuro.contacts.presentation.viewmodels.IContactListViewModel
import o.lizuro.utils.di.general.ViewModelFactory

@Module
class ContactListModule {
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