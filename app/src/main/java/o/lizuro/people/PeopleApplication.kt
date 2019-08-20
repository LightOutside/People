package o.lizuro.people

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import o.lizuro.core.IApp
import o.lizuro.core.di.IApplicationProvider
import o.lizuro.people.di.AppComponent
import javax.inject.Inject

class PeopleApplication : Application(), IApp, HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private val appComponent: AppComponent by lazy { AppComponent.Initializer.init(this@PeopleApplication) }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun getAppComponent(): IApplicationProvider = appComponent
}