package com.olizuro.mainscreen.di

import com.olizuro.mainscreen.domain.MainScreenUseCasesImpl
import dagger.Binds
import dagger.Module
import o.lizuro.core.mainscreen.IMainScreenUseCases

@Module
interface MainScreenModule {
    @Binds
    abstract fun bindsMainScreenApi(impl: MainScreenUseCasesImpl): IMainScreenUseCases
}