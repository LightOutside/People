package com.olizuro.mainscreen.presentation.viewmodels

import io.reactivex.Flowable
import o.lizuro.core.tools.ILogger
import o.lizuro.core.tools.INavigation
import o.lizuro.core.tools.INotifier
import o.lizuro.coreui.viewmodels.BaseViewModel
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val navigation: INavigation,
    private val notifier: INotifier
) : BaseViewModel(), IMainScreenViewModel {

    override val notifications: Flowable<String> = notifier.messagesFlow

    init {
        navigation.router.newRootScreen(navigation.getScreenContactList())
    }

    override fun setNavigator(navigator: SupportAppNavigator) {
        navigation.holder.setNavigator(navigator)
    }

    override fun removeNavigator() {
        navigation.holder.removeNavigator()
    }

    override fun navigateBack() {
        navigation.router.exit()
    }
}