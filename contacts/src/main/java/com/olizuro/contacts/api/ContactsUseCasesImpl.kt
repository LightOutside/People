package com.olizuro.contacts.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.olizuro.contacts.presentation.ContactListFragment
import o.lizuro.core.contacts.IContactsUseCases
import javax.inject.Inject

class ContactsUseCasesImpl @Inject constructor() : IContactsUseCases {

    override fun showContactsList(fragmentManager: FragmentManager, containerId: Int, backStack: Boolean) {
        val fragment = fragmentManager.findFragmentByTag(ContactListFragment.TAG) ?: ContactListFragment.create()
        addFragmentToManager(fragmentManager, containerId, backStack, fragment)
    }

    override fun showContactInfo(fragmentManager: FragmentManager, containerId: Int, backStack: Boolean, contactId: String) {
        //TODO Add fragment to fragmentManager
    }

    private fun addFragmentToManager(fragmentManager: FragmentManager, containerId: Int, backStack: Boolean, fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(containerId, fragment, ContactListFragment.TAG)
            if(backStack) {
                addToBackStack(null)
            }
            commit()
        }
    }
}