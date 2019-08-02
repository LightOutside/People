package o.lizuro.people

import android.app.Application
import o.lizuro.core.IApp
import o.lizuro.core.di.IApplicationProvider
import o.lizuro.core.tools.IToaster
import o.lizuro.people.di.AppComponent
import javax.inject.Inject

class PeopleApplication : Application(), IApp {
    @Inject
    lateinit var toaster: IToaster

    private val appComponent: AppComponent by lazy { AppComponent.Initializer.init(this@PeopleApplication) }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        toaster.show("app injected")
    }

    override fun getAppComponent(): IApplicationProvider = appComponent
}