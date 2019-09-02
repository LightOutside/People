package com.olizuro.mainscreen.presentation.viewmodels

import io.reactivex.Flowable
import ru.terrakok.cicerone.android.support.SupportAppNavigator

interface IMainScreenViewModel {
    val notifications: Flowable<String>

    fun setNavigator(navigator: SupportAppNavigator)
    fun removeNavigator()
    fun navigateBack()
}