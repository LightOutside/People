package com.olizuro.contacts.presentation.viewmodels

import androidx.lifecycle.ViewModel
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.repo.IRepoUseCases
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private val repoUseCases: IRepoUseCases,
    private var contactsUseCases: IContactsUseCases
) : ViewModel(), IContactListViewModel {


}