package com.olizuro.mainscreen.di

import androidx.lifecycle.ViewModelProviders
import com.olizuro.mainscreen.presentation.viewmodels.IMainScreenViewModel
import com.olizuro.mainscreen.presentation.viewmodels.MainScreenViewModel
import com.olizuro.mainscreen.presentation.views.MainActivity
import dagger.Module
import dagger.Provides
import o.lizuro.utils.di.general.ViewModelFactory

@Module
class MainActivityModule {
    @Provides
    fun providesIMainScreenViewModel(
        activity: MainActivity,
        factory: ViewModelFactory<MainScreenViewModel>
    ): IMainScreenViewModel {
        return ViewModelProviders.of(activity, factory).get(MainScreenViewModel::class.java)
    }
}