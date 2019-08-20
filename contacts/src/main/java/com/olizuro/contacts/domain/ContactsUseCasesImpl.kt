package com.olizuro.contacts.domain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.olizuro.contacts.R
import com.olizuro.contacts.presentation.views.ContactInfoFragment
import com.olizuro.contacts.presentation.views.ContactListFragment
import io.reactivex.Flowable
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import javax.inject.Inject

class ContactsUseCasesImpl @Inject constructor(
    private val repository: IRepository
) : IContactsUseCases {

    override fun loadContacts(forceRefresh: Boolean) {
        repository.loadContacts(forceRefresh)
    }

    override fun findContacts(pattern: String) : Flowable<List<Contact>> {
        return repository.findContacts(pattern)
    }

    override fun getDataState(): Flowable<DataState> {
        return repository.getDataState()
    }

    override fun getContact(id: String): Flowable<Contact> {
        return repository.getContact(id)
    }



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