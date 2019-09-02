package com.olizuro.contacts.presentation.views.list

import androidx.recyclerview.widget.DiffUtil
import com.olizuro.contacts.BR
import com.olizuro.contacts.R
import com.olizuro.contacts.presentation.viewmodels.list.ContactViewModel
import o.lizuro.coreui.views.recyclerview.DataBindingAdapter

class ContactListAdapter : DataBindingAdapter<ContactViewModel>(
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