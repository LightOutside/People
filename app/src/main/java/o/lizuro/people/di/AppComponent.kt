package o.lizuro.people.di

import dagger.Component
import o.lizuro.core.di.*
import o.lizuro.people.PeopleApplication
import javax.inject.Singleton

@Component(
    dependencies = [
        IToolsProvider::class,
        IRepositoryProvider::class,
        IContactsProvider::class
    ]
)
@Singleton
interface AppComponent : IApplicationProvider {

    fun inject(app: PeopleApplication)

    class Initializer private constructor() {
        companion object {

            fun init(app: PeopleApplication): AppComponent {

                val toolsProvider = ToolsComponent.Initializer.init(app)
                //val repositoryProvider = RepositoryComponent.Initializer.init()
                //val contactsProvider = ContactsComponent.Initializer.init()

                return DaggerAppComponent.builder()
                    .iToolsProvider(toolsProvider)
                    //.repositoryProvider(repositoryProvider)
                    //.contactsProvider(contactsProvider)
                    .build()
            }
        }
    }
}