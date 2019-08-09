package com.olizuro.contacts.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import o.lizuro.core.contacts.IContactInfoViewModel
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.ContactsState
import o.lizuro.core.repo.IRepoUseCases
import o.lizuro.core.tools.IErrorHandler
import javax.inject.Inject

class ContactInfoViewModel @Inject constructor(
    private var errorHandler: IErrorHandler,
    private var repoUseCases: IRepoUseCases
) : ViewModel(), IContactInfoViewModel {

    private val contactProcessor: BehaviorProcessor<Contact> = BehaviorProcessor.create()

    override fun getContact(): Flowable<Contact> {
        return contactProcessor
    }

    override fun setContactId(contactId: String) {
        contactProcessor.onNext(repoUseCases.getContact(contactId))
    }

    override fun showDialer(context: Context) {
        contactProcessor.value?.run {
            tryRunIntent(context, Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", this.phone, null)))
        }
    }

    private fun tryRunIntent(context: Context, intent: Intent) {
        val packageManager = context.packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.size > 0) {
            context.startActivity(intent)
        } else {
            errorHandler.notifyError("Отсутвует приложение для набора номера")
        }
    }
}