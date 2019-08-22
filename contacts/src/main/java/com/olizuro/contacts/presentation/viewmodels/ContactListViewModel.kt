package com.olizuro.contacts.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import o.lizuro.core.tools.INavigation
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private var contactsUseCases: IContactsUseCases,
    private var navigation: INavigation
) : ViewModel(), IContactListViewModel {

    private val inputProcessor = BehaviorProcessor.createDefault("")

    init {
        contactsUseCases.loadContacts(false)
    }

    override val contacts: Flowable<List<Contact>>
        get() = inputProcessor.distinctUntilChanged()
            .doOnNext {
                Log.d("QQQ","master: $it")
            }
            .switchMap {
                contactsUseCases.findContacts(it.toLowerCase())
            }
            .doOnNext {
                Log.d("QQQ","slave: $it.size")
            }


    override val dataState: Flowable<DataState>
        get() = contactsUseCases.getDataState()


    override fun inputTextChanged(text: String) {
        inputProcessor.onNext(text)
    }

    override fun pullToRefresh() {
        contactsUseCases.loadContacts(true)
    }

    override fun navigateToContactInfo(contactId: String) {
        navigation.router.navigateTo(navigation.getScreenContactInfo(contactId))
    }
}