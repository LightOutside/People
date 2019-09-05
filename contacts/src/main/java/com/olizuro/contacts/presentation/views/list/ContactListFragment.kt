package com.olizuro.contacts.presentation.views.list

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.olizuro.contacts.R
import com.olizuro.contacts.databinding.FragmentContactListBindingImpl
import com.olizuro.contacts.presentation.viewmodels.list.ContactViewModel
import com.olizuro.contacts.presentation.viewmodels.list.IContactListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_contact_list.*
import o.lizuro.core.tools.ILogger
import o.lizuro.coreui.views.fragment.BaseFragment
import o.lizuro.utils.context.getAttrColor
import o.lizuro.utils.rx.storeToComposite
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContactListFragment : BaseFragment<IContactListViewModel>() {

    companion object {
        private const val INPUT_DEBOUNCE = 500L //0.5s

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
        return DataBindingUtil.inflate<FragmentContactListBindingImpl>(
            inflater,
            R.layout.fragment_contact_list,
            container,
            false
        ).apply {
            lifecycleOwner = this@ContactListFragment
            vm = viewModel
        }.root
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
            adapter = ContactListAdapter()
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
        viewModel.contacts.observe(
            this,
            Observer<List<ContactViewModel>> {
                (list.adapter as ContactListAdapter).submitList(it)
            }
        )

        viewModel.isRefreshing.observe(
            this,
            Observer<Boolean> {
                pull_to_refresh.isRefreshing = it
            }
        )
    }
}