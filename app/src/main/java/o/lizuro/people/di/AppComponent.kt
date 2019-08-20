package o.lizuro.people.di

import com.olizuro.contacts.di.ContactsComponent
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import o.lizuro.core.di.*
import o.lizuro.people.PeopleApplication
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        IToolsProvider::class,
        IContactsProvider::class
    ],
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent : IApplicationProvider {

    fun inject(app: PeopleApplication)

    class Initializer private constructor() {
        companion object {

            fun init(app: PeopleApplication): AppComponent {

                val toolsProvider = ToolsComponent.Initializer.init(app)
                val contactsProvider = ContactsComponent.Initializer.init(toolsProvider)

                return DaggerAppComponent.builder()
                    .iToolsProvider(toolsProvider)
                    .iContactsProvider(contactsProvider)
                    .build()
            }
        }
    }
}

