package com.olizuro.mainscreen.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

import com.olizuro.mainscreen.R
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.core.tools.ILogger
import o.lizuro.utils.rx.storeToComposite
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var contactsUseCases: IContactsUseCases

    @Inject
    lateinit var errorHandler: IErrorHandler

    @Inject
    lateinit var logger: ILogger

    private val onCreateSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupErrorHandler()

        //contactsUseCases.showContactsList(supportFragmentManager, R.id.content, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        onCreateSubscriptions.clear()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private fun setupErrorHandler() {
        errorHandler.getErrorMessages()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Snackbar.make(findViewById(R.id.content), it, Snackbar.LENGTH_LONG).show()
                },
                {
                    logger.d("Oh, wait!")
                }
            ).storeToComposite(onCreateSubscriptions)
    }
}
