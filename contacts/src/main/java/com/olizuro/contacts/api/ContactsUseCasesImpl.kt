package com.olizuro.contacts.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.olizuro.contacts.R
import com.olizuro.contacts.presentation.views.ContactInfoFragment
import com.olizuro.contacts.presentation.views.ContactListFragment
import o.lizuro.core.contacts.IContactsUseCases
import javax.inject.Inject

class ContactsUseCasesImpl @Inject constructor() : IContactsUseCases {

    override fun showContactsList(fragmentManager: FragmentManager, containerId: Int, backStack: Boolean) {
        fragmentManager.findFragmentByTag(ContactListFragment.TAG) ?:
        addFragmentToManager(fragmentManager, containerId, backStack, ContactListFragment.create(), ContactListFragment.TAG)

    }

    override fun showContactInfo(fragmentManager: FragmentManager, containerId: Int, backStack: Boolean, contactId: String) {
        fragmentManager.findFragmentByTag(ContactInfoFragment.TAG) ?:
        addFragmentToManager(fragmentManager, containerId, backStack, ContactInfoFragment.create(contactId), ContactInfoFragment.TAG)
    }

    private fun addFragmentToManager(fragmentManager: FragmentManager, containerId: Int, backStack: Boolean, fragment: Fragment, tag: String) {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in_pop, R.anim.fragment_out_pop)
            add(containerId, fragment, tag)
            if(backStack) {
                addToBackStack(null)
            }
            commit()
        }
        fragmentManager.executePendingTransactions()
    }
}