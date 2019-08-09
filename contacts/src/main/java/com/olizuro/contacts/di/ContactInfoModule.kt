package com.olizuro.contacts.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.presentation.viewmodels.ContactInfoViewModel
import com.olizuro.contacts.presentation.views.ContactInfoFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import o.lizuro.core.contacts.IContactInfoViewModel

@Module
abstract class ContactInfoModule {
    @Module
    companion object {
        @Provides
        fun providesIContactListViewModel(
            fragment: ContactInfoFragment,
            factory: ViewModelProvider.Factory
        ): ContactInfoViewModel {
            return ViewModelProviders.of(fragment, factory)[ContactInfoViewModel::class.java]
        }
    }

    @Binds
    abstract fun bindsIContactInfoViewModel(viewModel: ContactInfoViewModel): IContactInfoViewModel

    @ContributesAndroidInjector
    abstract fun bindsContactInfoFragment(): ContactInfoFragment
}