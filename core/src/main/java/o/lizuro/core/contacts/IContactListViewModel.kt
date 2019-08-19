package o.lizuro.core.contacts

import androidx.fragment.app.FragmentManager
import io.reactivex.Flowable
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState

interface IContactListViewModel {
    val contacts: Flowable<List<Contact>>
    val dataState: Flowable<DataState>

    fun inputTextChanged(text: String)
    fun contactSelected(contactId: String, fragmentManager: FragmentManager)
    fun pullToRefresh()
}