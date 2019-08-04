package com.olizuro.contacts.api

import android.support.v7.app.AppCompatActivity
import o.lizuro.core.contacts.IContactsApi
import javax.inject.Inject

class ContactsApiImpl @Inject constructor() : IContactsApi {
    override fun showContactsList(activity: AppCompatActivity) {
        //TODO Add fragment to fragmentManager
    }

    override fun showContactInfo(activity: AppCompatActivity, contactId: String) {
        //TODO Add fragment to fragmentManager
    }
}