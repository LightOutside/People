package com.olizuro.contacts.di

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.presentation.viewmodels.info.ContactInfoViewModel
import com.olizuro.contacts.presentation.views.info.ContactInfoFragment
import dagger.Module
import dagger.Provides
import com.olizuro.contacts.presentation.viewmodels.info.IContactInfoViewModel
import o.lizuro.utils.di.general.ViewModelFactory

@Module
class ContactInfoFragmentModule {
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