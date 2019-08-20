package com.olizuro.contacts.di

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.presentation.viewmodels.ContactInfoViewModel
import com.olizuro.contacts.presentation.views.ContactInfoFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import o.lizuro.core.contacts.IContactInfoViewModel
import o.lizuro.utils.di.general.ViewModelFactory

@Module
class ContactInfoModule {
    @Provides
    fun provideViewModelInterface(
        fragment: ContactInfoFragment,
        factory: ViewModelFactory<ContactInfoViewModel>
    ): IContactInfoViewModel {
        return ViewModelProviders.of(fragment, factory).get(ContactInfoViewModel::class.java)
    }

    @Provides
    fun provideFragmentArguments(fragment: ContactInfoFragment): Bundle = fragment.arguments ?: Bundle()
}