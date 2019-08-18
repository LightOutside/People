package com.olizuro.contacts.presentation.views

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.olizuro.contacts.R
import com.olizuro.contacts.di.ContactListComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactListViewModel
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import o.lizuro.core.repo.IRepoUseCases
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.coreui.views.BaseFragment
import o.lizuro.utils.context.getAttrColor
import o.lizuro.utils.rx.storeToComposite
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContactListFragment : BaseFragment() {

    companion object {
        const val TAG = "com.olizuro.contacts.presentation.views.ContactListFragment"

        private const val INPUT_DEBOUNCE = 500L //ms

        fun create(): ContactListFragment {
            return ContactListFragment()
        }
    }

    @Inject
    lateinit var viewModel: IContactListViewModel

    @Inject
    lateinit var repoUseCases: IRepoUseCases

    @Inject
    lateinit var errorHandler: IErrorHandler

    private lateinit var input: SearchView
    private lateinit var pullToRefresh: SwipeRefreshLayout
    private lateinit var list: RecyclerView
    private lateinit var loader: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false).apply {
            input = findViewById<SearchView>(R.id.input).apply {
                setQuery("", false)
            }

            pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.pull_to_refresh).apply {
                setColorSchemeColors(
                    context.getAttrColor(R.attr.themePrimary)
                )
                setOnRefreshListener {
                    dropListData()
                    viewModel.pullToRefresh()
                }
            }

            list = findViewById<RecyclerView>(R.id.list).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ContactsAdapter(context) {
                    activity?.run {
                        viewModel.contactSelected(it, this.supportFragmentManager)
                    }
                }
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            loader = findViewById<ProgressBar>(R.id.loader).apply {
                indeterminateDrawable.setColorFilter(context.getAttrColor(R.attr.themePrimary), PorterDuff.Mode.SRC_IN)
            }
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

        viewModel.getContacts()
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

        viewModel.getContactsState()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    pullToRefresh.isRefreshing = false

                    when (it) {
                        DataState.LOADING -> {
                            list.visibility = GONE
                            loader.visibility = VISIBLE
                        }
                        DataState.LOADED -> {
                            list.visibility = VISIBLE
                            loader.visibility = GONE
                        }
                        else -> { /*do nothing*/
                        }
                    }
                },
                {
                    errorHandler.handleError(it)
                }
            ).storeToComposite(onStartSubscriptions)
    }

    override fun onAttach(context: Context) {
        activity?.let {
            ContactListComponent.Initializer
                .init((it.applicationContext as IApp).getAppComponent())
                .inject(this)
        }
        super.onAttach(context)
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