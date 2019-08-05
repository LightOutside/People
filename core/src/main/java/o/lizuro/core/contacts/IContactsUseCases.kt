package o.lizuro.core.contacts

import android.support.v7.app.AppCompatActivity

interface IContactsUseCases {
    fun showContactsList(activity: AppCompatActivity, containerId: Int)
    fun showContactInfo(activity: AppCompatActivity, containerId: Int, contactId: String)
}