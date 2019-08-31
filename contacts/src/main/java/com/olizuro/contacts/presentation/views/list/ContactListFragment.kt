package com.olizuro.contacts.presentation.views.list

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.olizuro.contacts.BR
import com.olizuro.contacts.R
import com.olizuro.contacts.presentation.viewmodels.list.ContactViewModel
import com.olizuro.contacts.presentation.viewmodels.list.IContactListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_contact_list.*
import o.lizuro.core.contacts.IContactsUseCases
import o.lizuro.core.entities.Contact
import o.lizuro.core.entities.DataState
import o.lizuro.core.tools.ILogger
import o.lizuro.coreui.views.fragment.BaseFragment
import o.lizuro.coreui.views.recyclerview.DataBindingAdapter
import o.lizuro.utils.context.getAttrColor
import o.lizuro.utils.rx.storeToComposite
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContactListFragment : BaseFragment<IContactListViewModel>() {

    companion object {
        private const val INPUT_DEBOUNCE = 500L //ms

        fun create(): ContactListFragment {
            return ContactListFragment()
        }
    }

    @Inject
    lateinit var logger: ILogger

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pull_to_refresh.apply {
            setColorSchemeColors(
                context.getAttrColor(R.attr.themePrimary)
            )
            setOnRefreshListener {
                viewModel.pullToRefresh()
            }
        }

        list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ContactsAdapter()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        loader.apply {
            indeterminateDrawable.setColorFilter(
                context.getAttrColor(R.attr.themePrimary),
                PorterDuff.Mode.SRC_IN
            )
        }

        input.apply {
            queryTextChanges()
                .debounce(INPUT_DEBOUNCE, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        viewModel.inputTextChanged(it.toString())
                    },
                    {
                        logger.d(it.message)
                    }
                ).storeToComposite(onCreateSubscriptions)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.contacts
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    (list.adapter as ContactsAdapter).submitList(it.map { ContactViewModel(it, viewModel::navigateToContactInfo) })
                },
                {
                    logger.d(it.message)
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
                        else -> { /*do nothing*/
                        }
                    }
                },
                {
                    logger.d(it.message)
                }
            ).storeToComposite(onStartSubscriptions)
    }

    inner class ContactsAdapter : DataBindingAdapter<ContactViewModel>(
        object : DiffUtil.ItemCallback<ContactViewModel>() {
            override fun areItemsTheSame(oldItem: ContactViewModel, newItem: ContactViewModel): Boolean {
                return oldItem.contact.id == newItem.contact.id
            }

            override fun areContentsTheSame(oldItem: ContactViewModel, newItem: ContactViewModel): Boolean {
                return true
            }
        }
    ) {
        override val itemId: Int = BR.item

        override fun getItemViewType(position: Int) = R.layout.view_contact_list_item
    }
}