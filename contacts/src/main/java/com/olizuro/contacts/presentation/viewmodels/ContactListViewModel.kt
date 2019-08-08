package com.olizuro.contacts.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.processors.BehaviorProcessor
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

    //private val subscriptions = CompositeDisposable()

    private val inputProcessor = BehaviorProcessor.createDefault("")

    init {
        repoUseCases.loadContacts(false)
    }

    override fun getContacts() : Flowable<List<Contact>> {
        return Flowable.combineLatest(
            inputProcessor,
            repoUseCases.getContacts(),
            BiFunction { text: String, contacts: List<Contact> ->
                if(text.isEmpty()) {
                    contacts
                } else {
                    contacts.filter {
                        it.name.toLowerCase().startsWith(text) || it.phone.startsWith(text)
                        //Regex("[^0-9]").replace(it.phone, "").startsWith(text)
                    }
                }
            }
        )
    }

    override fun getContactsState(): Flowable<ContactsState> {
        return repoUseCases.getContactsState()
    }

    override fun inputTextChanged(text: String) {
        inputProcessor.onNext(text.toLowerCase())
        Log.d("QQQ",text)
    }

    override fun contactSelected(contactId: String) {
        //TODO Show ContactInfo fragment
        //contactsUseCases.showContactInfo()
    }

    override fun pullToRefresh() {
        repoUseCases.loadContacts(true)
    }
}