package o.lizuro.core.di

import o.lizuro.core.mainscreen.IMainScreenUseCases

interface IMainScreenProvider {
    fun provideMainScreenUseCases(): IMainScreenUseCases
}