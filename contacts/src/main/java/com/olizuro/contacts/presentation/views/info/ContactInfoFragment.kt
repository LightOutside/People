package com.olizuro.contacts.presentation.views.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.olizuro.contacts.R
import com.olizuro.contacts.databinding.FragmentContactInfoBindingImpl
import com.olizuro.contacts.presentation.viewmodels.info.IContactInfoViewModel
import kotlinx.android.synthetic.main.fragment_contact_info.*
import o.lizuro.coreui.views.fragment.BaseFragment

class ContactInfoFragment : BaseFragment<IContactInfoViewModel>() {
    companion object {
        const val BUNDLE_CONTACT_ID = "com.olizuro.contacts.presentation.views.BUNDLE_CONTACT_ID"

        fun create(contactId: String): ContactInfoFragment {
            return ContactInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_CONTACT_ID, contactId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentContactInfoBindingImpl>(
            inflater,
            R.layout.fragment_contact_info,
            container,
            false
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ContactInfoFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.apply {
            setNavigationOnClickListener {
                viewModel.navigateBack()
            }
        }
    }
}