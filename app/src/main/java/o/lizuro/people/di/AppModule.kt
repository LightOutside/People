package o.lizuro.people.di

import com.olizuro.contacts.di.ContactInfoFragmentModule
import com.olizuro.contacts.di.ContactListFragmentModule
import com.olizuro.contacts.presentation.views.ContactInfoFragment
import com.olizuro.contacts.presentation.views.ContactListFragment
import com.olizuro.mainscreen.di.MainActivityModule
import com.olizuro.mainscreen.presentation.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import o.lizuro.utils.di.annotations.ActivityScope
import o.lizuro.utils.di.annotations.FragmentScope

@Module
abstract class AppModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeActivityAndroidInjector(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [ContactListFragmentModule::class])
    abstract fun contributeContactListFragment(): ContactListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ContactInfoFragmentModule::class])
    abstract fun contributeContactInfoFragment(): ContactInfoFragment
}