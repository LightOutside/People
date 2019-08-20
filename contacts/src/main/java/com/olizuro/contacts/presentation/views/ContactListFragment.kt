package com.olizuro.contacts.presentation.views

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.*
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.olizuro.contacts.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_contact_list.*
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.coreui.views.BaseFragment
import o.lizuro.utils.context.getAttrColor
import o.lizuro.utils.rx.storeToComposite
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContactListFragment : BaseFragment<IContactListViewModel>() {

    companion object {
        const val TAG = "com.olizuro.contacts.presentation.views.ContactListFragment"

        private const val INPUT_DEBOUNCE = 300L //ms

        fun create(): ContactListFragment {
            return ContactListFragment()
        }
    }

    @Inject
    lateinit var contactsUseCases: IContactsUseCases
    @Inject
    lateinit var errorHandler: IErrorHandler


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pull_to_refresh.apply {
            setColorSchemeColors(
                context.getAttrColor(R.attr.themePrimary)
            )
            setOnRefreshListener {
                //dropListData()
                viewModel.pullToRefresh()
            }
        }
        list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ContactsAdapter(context) {
                activity?.run {
                    viewModel.contactSelected(it, this.supportFragmentManager)
                }
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        loader.apply {
            indeterminateDrawable.setColorFilter(context.getAttrColor(R.attr.themePrimary), PorterDuff.Mode.SRC_IN)
        }
    }

    override fun onStart() {
        super.onStart()

        input.queryTextChanges()
            .debounce(INPUT_DEBOUNCE, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewModel.inputTextChanged(it.toString())
                },
                {
                    errorHandler.handleError(it)
                }
            ).storeToComposite(onStartSubscriptions)

        viewModel.contacts
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //dropListData()
                    (list.adapter as ContactsAdapter).submitList(it)
                },
                {
                    errorHandler.handleError(it)
                }
            ).storeToComposite(onStartSubscriptions)

        viewModel.dataState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    pull_to_refresh.isRefreshing = false

                    when (it) {
                        DataState.LOADING -> {
                            list.visibility = GONE
                            loader.visibility = VISIBLE
                        }
                        DataState.LOADED -> {
                            list.visibility = VISIBLE
                            loader.visibility = GONE
                        }
                        else -> { /*do nothing*/ }
                    }
                },
                {
                    errorHandler.handleError(it)
                }
            ).storeToComposite(onStartSubscriptions)
    }

    private fun dropListData() {
        (list.adapter as ContactsAdapter).submitList(null)
    }
}

private class ContactHolder(
    root: View,
    private val itemClick: (id: String) -> Unit
) : RecyclerView.ViewHolder(root) {
    private val root = root.findViewById<View>(R.id.root)
    private val name = root.findViewById<AppCompatTextView>(R.id.name)
    private val height = root.findViewById<AppCompatTextView>(R.id.height)
    private val phone = root.findViewById<AppCompatTextView>(R.id.phone)

    fun bind(contact: Contact) {
        root.setOnClickListener { itemClick(contact.id) }
        name.text = contact.name
        height.text = contact.height.toString()
        phone.text = contact.phone
    }
}

private class ContactsDiffUtilsCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return true
    }
}

private class ContactsAdapter(
    private val context: Context?,
    private val itemClick: (id: String) -> Unit
) : ListAdapter<Contact, ContactHolder>(ContactsDiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(
            LayoutInflater.from(context).inflate(R.layout.view_contact_list_item, parent, false),
            itemClick
        )
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(getItem(position))
    }
}