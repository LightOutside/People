package o.lizuro.people.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olizuro.contacts.presentation.viewmodels.ContactInfoViewModel
import com.olizuro.contacts.presentation.viewmodels.ContactListViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import o.lizuro.core.di.IContactsProvider
import o.lizuro.core.di.IRepoProvider
import o.lizuro.core.di.IToolsProvider
import o.lizuro.core.di.IViewModelFactoryProvider
import o.lizuro.core.tools.IErrorHandler
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

    @Binds
    @IntoMap
    @ViewModelKey(ContactInfoViewModel::class)
    abstract fun bindsContactInfoViewModel(viewModel: ContactInfoViewModel): ViewModel
}

@Singleton
@Component(
    dependencies = [IRepoProvider::class, IContactsProvider::class, IToolsProvider::class],
    modules = [ViewModelsModule::class]
)
interface ViewModelFactoryComponent : IViewModelFactoryProvider {
    class Initializer private constructor() {
        companion object {
            fun init(repoProvider: IRepoProvider, contactsProvider: IContactsProvider, toolsProvider: IToolsProvider): IViewModelFactoryProvider {
                return DaggerViewModelFactoryComponent
                    .builder()
                    .iRepoProvider(repoProvider)
                    .iContactsProvider(contactsProvider)
                    .iToolsProvider(toolsProvider)
                    .build()
            }
        }
    }
}