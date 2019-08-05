package com.olizuro.contacts.presentation

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olizuro.contacts.R
import com.olizuro.contacts.di.ContactsListFragmentComponent
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.repo.IRepoUseCases
import javax.inject.Inject

class ContactsListFragment : Fragment() {

    @Inject
    lateinit var repoUseCases: IRepoUseCases

    @Inject
    lateinit var contactsUseCases: IContactsUseCases

    companion object {
        const val TAG = "com.olizuro.contacts.presentation.ContactsListFragment"

        fun create(): ContactsListFragment {
            return ContactsListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        repoUseCases.loadContacts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false).apply {

        }
    }

    override fun onAttach(context: Context) {
        activity?.let {
            ContactsListFragmentComponent.Initializer
                .init((it.applicationContext as IApp).getAppComponent())
                .inject(this)
        }

        super.onAttach(context)
    }

}