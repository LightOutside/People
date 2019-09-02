package com.olizuro.mainscreen.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

import com.olizuro.mainscreen.R
import com.olizuro.mainscreen.presentation.viewmodels.IMainScreenViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
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
    lateinit var logger: ILogger

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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        viewModel.notifications
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Snackbar.make(content, it, Snackbar.LENGTH_LONG).show()
                },
                {
                    logger.d(it.message)
                }
            ).storeToComposite(onStartSubscriptions)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        viewModel.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeNavigator()
    }

    override fun onBackPressed() {
        viewModel.navigateBack()
    }
}
