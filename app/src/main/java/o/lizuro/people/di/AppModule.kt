package o.lizuro.people.di

import com.olizuro.contacts.di.ContactInfoModule
import com.olizuro.contacts.di.ContactListModule
import com.olizuro.contacts.presentation.views.ContactInfoFragment
import com.olizuro.contacts.presentation.views.ContactListFragment
import com.olizuro.mainscreen.di.MainScreenModule
import com.olizuro.mainscreen.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import o.lizuro.utils.di.annotations.ActivityScope
import o.lizuro.utils.di.annotations.FragmentScope

@Module
abstract class AppModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainScreenModule::class])
    abstract fun contributeActivityAndroidInjector(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [ContactListModule::class])
    abstract fun contributeContactListFragment(): ContactListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ContactInfoModule::class])
    abstract fun contributeContactInfoFragment(): ContactInfoFragment
}