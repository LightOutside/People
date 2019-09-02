package com.olizuro.contacts.presentation.viewmodels.list

import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState

interface IContactListViewModel {
    val contacts: MutableLiveData<List<ContactViewModel>>
    val isListVisible: MutableLiveData<Boolean>
    val isLoaderVisible: MutableLiveData<Boolean>
    val isRefreshing: MutableLiveData<Boolean>

    fun inputTextChanged(text: String)
    fun pullToRefresh()
}