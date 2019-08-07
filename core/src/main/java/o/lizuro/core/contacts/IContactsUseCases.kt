package o.lizuro.core.contacts

import androidx.fragment.app.FragmentManager

interface IContactsUseCases {
    fun showContactsList(fragmentManager: FragmentManager, containerId: Int, backStack: Boolean)
    fun showContactInfo(fragmentManager: FragmentManager, containerId: Int, backStack: Boolean, contactId: String)
}