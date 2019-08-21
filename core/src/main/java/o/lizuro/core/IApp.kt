package o.lizuro.core

import android.content.Context
import o.lizuro.core.di.IApplicationProvider
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

interface IApp {
    fun getApplicationContext(): Context
    fun getNavigatorHolder(): NavigatorHolder
    fun getRouter(): Router
    fun getAppComponent(): IApplicationProvider
}