package o.lizuro.people.di

import androidx.lifecycle.ReportFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.presentation.ContactListFragment
import com.olizuro.contacts.presentation.viewmodels.ContactListViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.di.IContactsProvider
import o.lizuro.core.di.IRepoProvider
import o.lizuro.core.di.IViewModelsProvider
import o.lizuro.utils.di.annotations.ViewModelKey
import o.lizuro.utils.di.general.ViewModelFactory
import javax.inject.Singleton

@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel::class)
    abstract fun bindsContactListViewModel(viewModel: ContactListViewModel): ViewModel

//    @Binds
//    abstract fun bindsIContactListViewModel(viewModel: ContactListViewModel): IContactListViewModel


}

@Singleton
@Component(
    dependencies = [IRepoProvider::class, IContactsProvider::class],
    modules = [ViewModelsModule::class]
)
interface ViewModelsComponent : IViewModelsProvider {
    class Initializer private constructor() {
        companion object {
            fun init(repoProvider: IRepoProvider, contactsProvider: IContactsProvider): IViewModelsProvider {
                return DaggerViewModelsComponent
                    .builder()
                    .iRepoProvider(repoProvider)
                    .iContactsProvider(contactsProvider)
                    .build()
            }
        }
    }
}