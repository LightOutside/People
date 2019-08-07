package com.olizuro.contacts.di

import com.olizuro.contacts.presentation.ContactListFragment
import com.olizuro.contacts.presentation.viewmodels.ContactListViewModel
import dagger.Component
import o.lizuro.core.di.IApplicationProvider
import o.lizuro.core.di.IViewModelsProvider
import o.lizuro.utils.di.annotations.FragmentScope

@FragmentScope
@Component(
    dependencies = [IViewModelsProvider::class],
    modules = [ContactListModule::class]
)
interface ContactListComponent {
    //fun inject(viewModel: ContactListViewModel)
    fun inject(fragment: ContactListFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                viewModelsProvider: IViewModelsProvider
            ): ContactListComponent {
                return DaggerContactListComponent.builder()
                    .iViewModelsProvider(viewModelsProvider)
                    .build()
            }
        }
    }
}