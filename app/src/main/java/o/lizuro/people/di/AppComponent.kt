package o.lizuro.people.di

import com.olizuro.contacts.di.ContactsUseCasesComponent
import com.olizuro.repo.di.RepoComponent
import dagger.Component
import o.lizuro.core.di.*
import o.lizuro.people.PeopleApplication
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        IToolsProvider::class,
        IRepoProvider::class,
        IContactsProvider::class,
        IViewModelFactoryProvider::class
    ]
)
interface AppComponent : IApplicationProvider {

    fun inject(app: PeopleApplication)

    class Initializer private constructor() {
        companion object {

            fun init(app: PeopleApplication): AppComponent {

                val toolsProvider = ToolsComponent.Initializer.init(app)
                val repoProvider = RepoComponent.Initializer.init(toolsProvider)
                val contactsProvider = ContactsUseCasesComponent.Initializer.init()
                val viewModelFactoryProvider = ViewModelFactoryComponent.Initializer.init(repoProvider, contactsProvider)

                return DaggerAppComponent.builder()
                    .iToolsProvider(toolsProvider)
                    .iRepoProvider(repoProvider)
                    .iContactsProvider(contactsProvider)
                    .iViewModelFactoryProvider(viewModelFactoryProvider)
                    .build()
            }
        }
    }
}