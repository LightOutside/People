package o.lizuro.core.contacts

import androidx.fragment.app.FragmentManager
import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState

interface IContactListViewModel {
    fun getContacts() : Flowable<List<Contact>>
    fun getContactsState() : Flowable<DataState>

    fun inputTextChanged(text: String)
    fun contactSelected(contactId: String, fragmentManager: FragmentManager)
    fun pullToRefresh()
}