package o.lizuro.people

import android.app.Application
import o.lizuro.core.IApp
import o.lizuro.core.di.IApplicationProvider
import o.lizuro.people.di.AppComponent

class PeopleApplication : Application(), IApp {

    private val appComponent: AppComponent by lazy { AppComponent.Initializer.init(this@PeopleApplication) }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun getAppComponent(): IApplicationProvider = appComponent
}