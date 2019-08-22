package com.olizuro.contacts.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.core.tools.INavigation
import javax.inject.Inject

class ContactInfoViewModel @Inject constructor(
    private var errorHandler: IErrorHandler,
    private var contactsUseCases: IContactsUseCases,
    private var navigation: INavigation
) : ViewModel(), IContactInfoViewModel {

    override fun getContact(id: String): Flowable<Contact> {
        return contactsUseCases.getContact(id)
    }

    override fun showDialer(context: Context, phone: String) {
        tryRunIntent(context, Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)))
    }

    override fun navigateBack() {
        navigation.router.backTo(navigation.getScreenContactList())
    }

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