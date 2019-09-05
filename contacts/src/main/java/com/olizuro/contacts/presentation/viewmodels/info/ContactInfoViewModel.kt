package com.olizuro.contacts.presentation.viewmodels.info

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.olizuro.contacts.presentation.views.info.ContactInfoFragment.Companion.BUNDLE_CONTACT_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.tools.ILogger
import o.lizuro.core.tools.INavigation
import o.lizuro.core.tools.ISystemNavigator
import o.lizuro.utils.rx.storeToComposite
import o.lizuro.coreui.viewmodels.BaseViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject

class ContactInfoViewModel @Inject constructor(
    private val arguments: Bundle,
    private val logger: ILogger,
    private val contactsUseCases: IContactsUseCases,
    private val navigation: INavigation,
    private val systemNavigator: ISystemNavigator
) : BaseViewModel(), IContactInfoViewModel {

    init {
        setupContact()
    }

    override val name: MutableLiveData<String> = MutableLiveData()
    override val phone: MutableLiveData<String> = MutableLiveData()
    override val temperament: MutableLiveData<String> = MutableLiveData()
    override val educationPeriod: MutableLiveData<String> = MutableLiveData()
    override val biography: MutableLiveData<String> = MutableLiveData()

    override fun showDialer() {
        systemNavigator.showDialer(phone.value)
    }

    override fun navigateBack() {
        navigation.router.backTo(navigation.getScreenContactList())
    }

    private fun setupContact() {
        contactsUseCases.getContact(
            arguments.getString(BUNDLE_CONTACT_ID) ?: throw Exception("Missing contactId argument")
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    name.value = it.name
                    phone.value = it.phone
                    temperament.value = it.temperament.value.capitalize()

                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    val formatter = SimpleDateFormat("dd.MM.yyyy")

                    val date1 = parser.parse(it.educationPeriod.start)
                    val date2 = parser.parse(it.educationPeriod.end)
                    educationPeriod.value = if (date1 < date2) {
                        "${formatter.format(date1)} - ${formatter.format(date2)}"
                    } else {
                        "${formatter.format(date2)} - ${formatter.format(date1)}"
                    }

                    biography.value = it.biography
                },
                {
                    logger.d(it.message)
                }
            ).storeToComposite(subscriptions)
    }
}