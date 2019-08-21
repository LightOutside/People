package com.olizuro.contacts.presentation.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private var contactsUseCases: IContactsUseCases
) : ViewModel(), IContactListViewModel {

    init {
        contactsUseCases.loadContacts(false)
    }

    private val inputProcessor = BehaviorProcessor.createDefault("")

    override val contacts: Flowable<List<Contact>>
        get() = inputProcessor.switchMap {
            contactsUseCases.findContacts(it.toLowerCase())
        }

    override val dataState: Flowable<DataState>
        get() = contactsUseCases.getDataState()


    override fun inputTextChanged(text: String) {
        inputProcessor.onNext(text)
    }

    override fun pullToRefresh() {
        contactsUseCases.loadContacts(true)
    }
}