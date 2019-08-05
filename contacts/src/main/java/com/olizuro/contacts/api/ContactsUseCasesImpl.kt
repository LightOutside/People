package com.olizuro.contacts.api

import androidx.appcompat.app.AppCompatActivity
import com.olizuro.contacts.presentation.ContactsListFragment
import o.lizuro.core.contacts.IContactsUseCases
import javax.inject.Inject

class ContactsUseCasesImpl @Inject constructor() : IContactsUseCases {

    override fun showContactsList(activity: AppCompatActivity, containerId: Int) {
        val fragmentManager = activity.supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(ContactsListFragment.TAG) ?: ContactsListFragment.create()

        fragmentManager.beginTransaction().apply {
            replace(containerId, fragment, ContactsListFragment.TAG)
            addToBackStack(null)
            commit()
        }

    }

    override fun showContactInfo(activity: AppCompatActivity, containerId: Int, contactId: String) {
        //TODO Add fragment to fragmentManager
    }
}