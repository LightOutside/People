package com.olizuro.contacts.presentation.viewmodels.info

import androidx.lifecycle.MutableLiveData

interface IContactInfoViewModel {
    val name: MutableLiveData<String>
    val phone: MutableLiveData<String>
    val temperament: MutableLiveData<String>
    val educationPeriod: MutableLiveData<String>
    val biography: MutableLiveData<String>

    fun showDialer()
    fun navigateBack()
}