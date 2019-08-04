package com.olizuro.mainscreen.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.olizuro.mainscreen.R
import com.olizuro.mainscreen.di.MainScreenComponent
import o.lizuro.core.IApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainScreenComponent.Initializer
            .init((applicationContext as IApp).getAppComponent())
            .inject(this)

        setContentView(R.layout.activity_main)
    }
}
