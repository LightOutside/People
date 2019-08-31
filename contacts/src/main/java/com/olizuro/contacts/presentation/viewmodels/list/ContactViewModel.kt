package com.olizuro.contacts.presentation.viewmodels.list

import androidx.lifecycle.MutableLiveData
import o.lizuro.core.entities.Contact

class ContactViewModel(
    val contact: Contact,
    private val onClick: (id: String) -> Unit
) {
    var name: MutableLiveData<String> = MutableLiveData()
    var phone: MutableLiveData<String> = MutableLiveData()
    var height: MutableLiveData<String> = MutableLiveData()

    init {
        name.value = contact.name
        phone.value = contact.phone
        height.value = contact.height.toString()
    }

    fun showInfo() {
        onClick(contact.id)
    }
}