package com.olizuro.mainscreen.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar

import com.olizuro.mainscreen.R
import com.olizuro.mainscreen.presentation.viewmodels.IMainScreenViewModel
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.tools.INotifier
import o.lizuro.core.tools.ILogger
import o.lizuro.core.tools.INavigation
import o.lizuro.coreui.views.activity.BaseActivity
import o.lizuro.utils.rx.storeToComposite
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject


class MainActivity : BaseActivity<IMainScreenViewModel>() {

    @Inject
    lateinit var contactsUseCases: IContactsUseCases

    @Inject
    lateinit var notifier: INotifier

    @Inject
    lateinit var logger: ILogger

    @Inject
    lateinit var navigation: INavigation

    private val onCreateSubscriptions = CompositeDisposable()

    private val navigator = object : SupportAppNavigator(this, R.id.nav_container) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            fragmentTransaction?.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in_pop, R.anim.fragment_out_pop)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupNotifier()

        navigation.router.newRootScreen(navigation.getScreenContactList())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigation.holder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigation.holder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        onCreateSubscriptions.clear()
    }

    override fun onBackPressed() {
        navigation.router.exit()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private fun setupNotifier() {
        notifier.messagesFlow
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Snackbar.make(findViewById(R.id.content), it, Snackbar.LENGTH_LONG).show()
                },
                {
                    logger.d(it.message)
                }
            ).storeToComposite(onCreateSubscriptions)
    }
}
