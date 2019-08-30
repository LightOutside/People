package com.olizuro.contacts.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.olizuro.contacts.presentation.views.ContactInfoFragment.Companion.BUNDLE_CONTACT_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.core.tools.INavigation
import o.lizuro.utils.rx.storeToComposite
import viewmodels.BaseViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject

class ContactInfoViewModel @Inject constructor(
    private var arguments: Bundle,
    private var errorHandler: IErrorHandler,
    private var contactsUseCases: IContactsUseCases,
    private var navigation: INavigation
) : BaseViewModel(), IContactInfoViewModel {

    init {
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
                { e ->
                    errorHandler.handleError(e)
                }
            ).storeToComposite(subscriptions)
    }

    override var name: MutableLiveData<String> = MutableLiveData()
    override var phone: MutableLiveData<String> = MutableLiveData()
    override var temperament: MutableLiveData<String> = MutableLiveData()
    override var educationPeriod: MutableLiveData<String> = MutableLiveData()
    override var biography: MutableLiveData<String> = MutableLiveData()

//    fun showDialer(context: Context, phone: String) {
//        tryRunIntent(context, Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)))
//    }

    override fun showDialer() {
        errorHandler.notifyError("Here!")
    }

    override fun navigateBack() {
        navigation.router.backTo(navigation.getScreenContactList())
    }

    //TODO Move to navigator
    private fun tryRunIntent(context: Context, intent: Intent) {
        val packageManager = context.packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.size > 0) {
            context.startActivity(intent)
        } else {
            errorHandler.notifyError("Отсутствует приложение для набора номера")
        }
    }
}