package com.olizuro.contacts.presentation.viewmodels.list

import androidx.lifecycle.MutableLiveData
import o.lizuro.core.entities.Contact

class ContactViewModel(
    val contact: Contact,
    private val onClick: (id: String) -> Unit
) {
    val name: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()
    val height: MutableLiveData<String> = MutableLiveData()

    init {
        name.value = contact.name
        phone.value = contact.phone
        height.value = contact.height.toString()
    }

    fun showInfo() {
        onClick(contact.id)
    }
}