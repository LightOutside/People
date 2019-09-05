package o.lizuro.people.tools

import androidx.fragment.app.Fragment
import com.olizuro.contacts.presentation.views.info.ContactInfoFragment
import com.olizuro.contacts.presentation.views.list.ContactListFragment
import o.lizuro.core.tools.INavigation
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

class NavigationImpl @Inject constructor() : INavigation {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    override val holder: NavigatorHolder
        get() = cicerone.navigatorHolder

    override val router: Router
        get() = cicerone.router

    override fun getScreenContactList(): SupportAppScreen {
        return object : SupportAppScreen() {
            init {
                screenKey = "ContactList"
            }

            override fun getFragment(): Fragment {
                return ContactListFragment.create()
            }
        }
    }

    override fun getScreenContactInfo(contactId: String): SupportAppScreen {
        return object : SupportAppScreen() {
            init {
                screenKey = "ContactInfo"
            }

            override fun getFragment(): Fragment {
                return ContactInfoFragment.create(contactId)
            }
        }
    }
}