package o.lizuro.people

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import o.lizuro.core.IApp
import o.lizuro.core.di.IApplicationProvider
import o.lizuro.people.di.AppComponent
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PeopleApplication : Application(), IApp, HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private val appComponent: AppComponent by lazy { AppComponent.Initializer.init(this@PeopleApplication) }

    lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        cicerone = Cicerone.create()
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun getAppComponent(): IApplicationProvider = appComponent

    override fun getNavigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }

    override fun getRouter(): Router {
        return cicerone.router
    }
}