package o.lizuro.core.contacts

import androidx.fragment.app.FragmentManager
import io.reactivex.Flowable
import o.lizuro.core.entities.ContactsState

interface IContactListViewModel {
    fun getContacts() : Flowable<List<String>>
    fun getContactsState() : Flowable<ContactsState>

    fun inputTextChanged(text: String)
    fun contactSelected(contactId: String, fragmentManager: FragmentManager)
    fun pullToRefresh()
}