package o.lizuro.core.contacts

import android.support.v7.app.AppCompatActivity

interface IContactsApi {
    fun showContactsList(activity: AppCompatActivity)
    fun showContactInfo(activity: AppCompatActivity, contactId: String)
}