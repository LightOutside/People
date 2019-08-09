package com.olizuro.mainscreen.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

import com.olizuro.mainscreen.R
import com.olizuro.mainscreen.di.MainScreenComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.core.tools.ILogger
import o.lizuro.utils.rx.storeToComposite
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var contactsUseCases: IContactsUseCases

    @Inject
    lateinit var errorHandler: IErrorHandler

    @Inject
    lateinit var logger: ILogger

    private val onCreateSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainScreenComponent.Initializer
            .init((applicationContext as IApp).getAppComponent())
            .inject(this)

        setContentView(R.layout.activity_main)

        setupErrorHandler()


        contactsUseCases.showContactsList(supportFragmentManager, R.id.content, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        onCreateSubscriptions.clear()
    }

    private fun setupErrorHandler() {
        errorHandler.getErrorMessages()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Snackbar.make(findViewById<View>(R.id.content), it, Snackbar.LENGTH_LONG).show()
                },
                {
                    logger.d("Oh, wait!")
                }
            ).storeToComposite(onCreateSubscriptions)
    }
}
