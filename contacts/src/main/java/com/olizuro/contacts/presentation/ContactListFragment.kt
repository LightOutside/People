package com.olizuro.contacts.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.olizuro.contacts.R
import com.olizuro.contacts.di.ContactListComponent
import com.olizuro.contacts.presentation.viewmodels.ContactListViewModel
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.repo.IRepoUseCases
import javax.inject.Inject

class ContactListFragment : Fragment() {

    companion object {
        const val TAG = "com.olizuro.contacts.presentation.ContactListFragment"

        fun create(): ContactListFragment {
            return ContactListFragment()
        }
    }

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModel: IContactListViewModel

    //private lateinit var viewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val k = viewModel

 //       viewModel = ViewModelProviders.of(this, viewModelFactory)[ContactListViewModel::class.java]
//        model.getUsers().observe(this, Observer<List<User>>{ users ->
//            // update UI
//        })

        //repoUseCases.loadContacts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false).apply {

        }
    }

    override fun onAttach(context: Context) {
        activity?.let {
            ContactListComponent.Initializer
                .init((it.applicationContext as IApp).getAppComponent())
                .inject(this)
        }

        super.onAttach(context)
    }

}