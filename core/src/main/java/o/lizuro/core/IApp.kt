package o.lizuro.core

import android.content.Context
import o.lizuro.core.di.IApplicationProvider

interface IApp {
    fun getApplicationContext(): Context
    fun getAppComponent(): IApplicationProvider
}