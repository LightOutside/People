package com.olizuro.contacts.di

import com.olizuro.contacts.presentation.ContactInfoFragment
import dagger.Component
import o.lizuro.core.di.IRepoProvider
import o.lizuro.utils.di.annotations.FragmentScope

@FragmentScope
@Component(
    dependencies = [IRepoProvider::class],
    modules = [ContactInfoFragmentModule::class]
)
interface ContactInfoFragmentComponent {
    fun inject(fragment: ContactInfoFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                repoProvider: IRepoProvider
            ): ContactInfoFragmentComponent {
                return DaggerContactInfoFragmentComponent.builder()
                    .iRepoProvider(repoProvider)
                    .build()
            }
        }
    }
}

