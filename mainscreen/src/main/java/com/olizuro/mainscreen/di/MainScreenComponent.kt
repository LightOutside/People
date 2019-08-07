package com.olizuro.mainscreen.di

import com.olizuro.mainscreen.presentation.MainActivity
import dagger.Component
import o.lizuro.core.di.IApplicationProvider
import o.lizuro.utils.di.annotations.ActivityScope

@ActivityScope
@Component(
    dependencies = [IApplicationProvider::class],
    modules = [MainScreenModule::class]
)
interface MainScreenComponent {
    fun inject(activity: MainActivity)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: IApplicationProvider): MainScreenComponent =
                DaggerMainScreenComponent.builder()
                    .iApplicationProvider(appComponent)
                    .build()
        }
    }
}