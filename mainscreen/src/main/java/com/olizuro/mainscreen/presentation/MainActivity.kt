package com.olizuro.mainscreen.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.olizuro.mainscreen.R
import com.olizuro.mainscreen.di.MainScreenComponent
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactsUseCases
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var contactsUseCases: IContactsUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainScreenComponent.Initializer
            .init((applicationContext as IApp).getAppComponent())
            .inject(this)

        setContentView(R.layout.activity_main)

        contactsUseCases.showContactsList(this, R.id.content_container)
    }
}