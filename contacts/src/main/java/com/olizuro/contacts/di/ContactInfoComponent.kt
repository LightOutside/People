package com.olizuro.contacts.di

import com.olizuro.contacts.presentation.views.ContactInfoFragment
import dagger.Component
import o.lizuro.core.di.IApplicationProvider
import o.lizuro.core.di.IRepoProvider
import o.lizuro.utils.di.annotations.FragmentScope

@FragmentScope
@Component(
    dependencies = [IApplicationProvider::class],
    modules = [ContactInfoModule::class]
)
interface ContactInfoComponent {
    fun inject(fragment: ContactInfoFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                applicationProvider: IApplicationProvider
            ): ContactInfoComponent {
                return DaggerContactInfoComponent.builder()
                    .iApplicationProvider(applicationProvider)
                    .build()
            }
        }
    }
}

