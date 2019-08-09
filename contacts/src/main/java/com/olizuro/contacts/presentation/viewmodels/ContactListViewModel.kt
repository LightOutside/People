package com.olizuro.contacts.presentation.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState
import o.lizuro.core.repo.IRepoUseCases
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private var repoUseCases: IRepoUseCases,
    private var contactsUseCases: IContactsUseCases
) : ViewModel(), IContactListViewModel {


    init {
        repoUseCases.loadContacts(false)
    }

    override fun getContacts(): Flowable<List<Contact>> {
        return repoUseCases.getContacts().map { it.map { id -> repoUseCases.getContact(id) } }
    }

    override fun getContactsState(): Flowable<ContactsState> {
        return repoUseCases.getContactsState()
    }

    override fun inputTextChanged(text: String) {
        repoUseCases.setContactsPrefix(text.toLowerCase())
    }

    override fun contactSelected(contactId: String) {
        //TODO Show ContactInfo fragment
        //contactsUseCases.showContactInfo()
    }

    override fun pullToRefresh() {
        repoUseCases.loadContacts(true)
    }
}