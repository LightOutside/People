package com.olizuro.contacts.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olizuro.contacts.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_contact_info.*
import com.olizuro.contacts.presentation.viewmodels.IContactInfoViewModel
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.coreui.views.BaseFragment
import o.lizuro.utils.rx.storeToComposite
import java.text.SimpleDateFormat
import javax.inject.Inject

class ContactInfoFragment : BaseFragment<IContactInfoViewModel>() {
    companion object {
        private const val BUNDLE_CONTACT_ID = "com.olizuro.contacts.presentation.views.BUNDLE_CONTACT_ID"

        fun create(contactId: String): ContactInfoFragment {
            return ContactInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_CONTACT_ID, contactId)
                }
            }
        }
    }

    @Inject
    lateinit var errorHandler: IErrorHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.apply {
            setNavigationOnClickListener {
                viewModel.navigateBack()
            }
        }
        phone.apply {
            setOnClickListener { viewModel.showDialer(context, phone.text.toString()) }
        }
    }

    override fun onStart() {
        super.onStart()

        arguments?.getString(BUNDLE_CONTACT_ID)?.let { contactId ->
            viewModel.getContact(contactId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        name.text = it.name
                        phone.text = it.phone
                        temperament.text = it.temperament.value.capitalize()

                        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val formatter = SimpleDateFormat("dd.MM.yyyy")

                        val date1 = parser.parse(it.educationPeriod.start)
                        val date2 = parser.parse(it.educationPeriod.end)
                        education_period.text = if(date1 < date2) {
                            "${formatter.format(date1)} - ${formatter.format(date2)}"
                        } else {
                            "${formatter.format(date2)} - ${formatter.format(date1)}"
                        }

                        biography.text = it.biography
                    },
                    {
                        errorHandler.handleError(it)
                    }
                ).storeToComposite(onStartSubscriptions)
        }
    }
}