package com.olizuro.contacts.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import o.lizuro.core.entities.Contact

interface IContactInfoViewModel {
    //fun getContact(id: String) : Flowable<Contact>
    val name: LiveData<String>
    fun showDialer(context: Context, phone: String)
    fun showDialer()
    fun navigateBack()
}