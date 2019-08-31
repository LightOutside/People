package com.olizuro.contacts.presentation.viewmodels.list

import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState

interface IContactListViewModel {
    val contacts: Flowable<List<Contact>>
    val dataState: Flowable<DataState>

    fun inputTextChanged(text: String)
    fun navigateToContactInfo(contactId: String)
    fun pullToRefresh()
}