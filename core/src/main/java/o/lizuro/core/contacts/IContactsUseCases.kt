package o.lizuro.core.contacts

import androidx.appcompat.app.AppCompatActivity

interface IContactsUseCases {
    fun showContactsList(activity: AppCompatActivity, containerId: Int)
    fun showContactInfo(activity: AppCompatActivity, containerId: Int, contactId: String)
}