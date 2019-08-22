package o.lizuro.core.tools

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

interface INavigation {
    val holder: NavigatorHolder
    val router: Router
    fun getScreenContactList(): SupportAppScreen
    fun getScreenContactInfo(contactId: String): SupportAppScreen
}