package com.olizuro.contacts.presentation.viewmodels.list

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.DataState
import o.lizuro.core.tools.ILogger
import o.lizuro.core.tools.INavigation
import o.lizuro.coreui.viewmodels.BaseViewModel
import o.lizuro.utils.rx.storeToComposite
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private val contactsUseCases: IContactsUseCases,
    private val navigation: INavigation,
    private val logger: ILogger
) : BaseViewModel(), IContactListViewModel {

    private val inputProcessor = BehaviorProcessor.createDefault("")

    override val contacts: MutableLiveData<List<ContactViewModel>> = MutableLiveData()
    override val isListVisible: MutableLiveData<Boolean> = MutableLiveData()
    override val isLoaderVisible: MutableLiveData<Boolean> = MutableLiveData()
    override val isErrorVisible: MutableLiveData<Boolean> = MutableLiveData()
    override val isRefreshing: MutableLiveData<Boolean> = MutableLiveData()

    init {
        setupContacts()
        setupContactsState()
        contactsUseCases.loadContacts(false)
    }

    override fun inputTextChanged(text: String) {
        inputProcessor.onNext(text)
    }

    override fun pullToRefresh() {
        contactsUseCases.loadContacts(true)
    }

    private fun navigateToContactInfo(contactId: String) {
        navigation.router.navigateTo(navigation.getScreenContactInfo(contactId))
    }

    private fun setupContacts() {
        inputProcessor.distinctUntilChanged()
            .switchMap { contactsUseCases.findContacts(it.toLowerCase()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { contactList ->
                    contacts.value =
                        contactList.map { ContactViewModel(it, this::navigateToContactInfo) }
                },
                {
                    logger.d(it.message)
                }
            ).storeToComposite(subscriptions)
    }

    private fun setupContactsState() {
        contactsUseCases.getDataState()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isRefreshing.value = false

                    when (it) {
                        DataState.LOADING -> {
                            isListVisible.value = false
                            isLoaderVisible.value = true
                            isErrorVisible.value = false
                        }
                        DataState.LOADED -> {
                            isListVisible.value = true
                            isLoaderVisible.value = false
                            isErrorVisible.value = false
                        }
                        DataState.ERROR -> {
                            isListVisible.value = false
                            isLoaderVisible.value = false
                            isErrorVisible.value = true
                        }
                        else -> { /*do nothing*/
                        }
                    }
                },
                {
                    logger.d(it.message)
                }
            ).storeToComposite(subscriptions)
    }
}