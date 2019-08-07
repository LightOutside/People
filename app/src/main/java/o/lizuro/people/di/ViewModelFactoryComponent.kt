package o.lizuro.people.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olizuro.contacts.presentation.viewmodels.ContactListViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import o.lizuro.core.di.IContactsProvider
import o.lizuro.core.di.IRepoProvider
import o.lizuro.core.di.IViewModelFactoryProvider
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

}

@Singleton
@Component(
    dependencies = [IRepoProvider::class, IContactsProvider::class],
    modules = [ViewModelsModule::class]
)
interface ViewModelFactoryComponent : IViewModelFactoryProvider {
    class Initializer private constructor() {
        companion object {
            fun init(repoProvider: IRepoProvider, contactsProvider: IContactsProvider): IViewModelFactoryProvider {
                return DaggerViewModelFactoryComponent
                    .builder()
                    .iRepoProvider(repoProvider)
                    .iContactsProvider(contactsProvider)
                    .build()
            }
        }
    }
}