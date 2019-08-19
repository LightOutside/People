package com.olizuro.contacts.presentation.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.olizuro.contacts.R
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import o.lizuro.core.repo.IRepoUseCases
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private var repoUseCases: IRepoUseCases,
    private var contactsUseCases: IContactsUseCases
) : ViewModel(), IContactListViewModel {

    init {
        repoUseCases.loadContacts(false)
    }

    private val inputProcessor = BehaviorProcessor.createDefault("")

    override val contacts: Flowable<List<Contact>>
        get() = inputProcessor.switchMap {
            repoUseCases.findContacts(it.toLowerCase())
        }

    override val dataState: Flowable<DataState>
        get() = repoUseCases.getDataState()


    override fun inputTextChanged(text: String) {
        inputProcessor.onNext(text)
    }

    override fun contactSelected(contactId: String, fragmentManager: FragmentManager) {
        contactsUseCases.showContactInfo(fragmentManager, R.id.content, true, contactId)
    }

    override fun pullToRefresh() {
        repoUseCases.loadContacts(true)
    }
}