package com.olizuro.contacts.presentation.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olizuro.contacts.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_contact_info.*
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactInfoViewModel
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.coreui.views.BaseFragment
import o.lizuro.utils.rx.storeToComposite
import java.text.SimpleDateFormat
import javax.inject.Inject

class ContactInfoFragment : BaseFragment<IContactInfoViewModel>() {

    companion object {
        const val TAG = "com.olizuro.contacts.presentation.views.ContactInfoFragment"

        const val BUNDLE_CONTACT_ID = "com.olizuro.contacts.presentation.views.BUNDLE_CONTACT_ID"

        fun create(contactId: String): ContactInfoFragment {
            return ContactInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_CONTACT_ID, contactId)
                }
            }
        }
    }

//    @Inject
//    lateinit var viewModel: IContactInfoViewModel

    @Inject
    lateinit var errorHandler: IErrorHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                //TODO Navigation framework
                activity?.run {
                    onBackPressed()
                }
            }
        }
        phone.apply {
            setOnClickListener { viewModel.showDialer(context, phone.text.toString()) }
        }
    }

    override fun onStart() {
        super.onStart()

        arguments?.getString(BUNDLE_CONTACT_ID)?.let { id ->
            viewModel.getContact(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        name.text = it.name
                        phone.text = it.phone
                        temperament.text = it.temperament.value.capitalize()

                        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val formatter = SimpleDateFormat("dd.MM.yyyy")
                        education_period.text =
                            "${formatter.format(parser.parse(it.educationPeriod.start))} - ${formatter.format(parser.parse(it.educationPeriod.end))}"

                        biography.text = it.biography
                    },
                    {
                        errorHandler.handleError(it)
                    }
                ).storeToComposite(onStartSubscriptions)
        }
    }

//    override fun onAttach(context: Context) {
//        activity?.let {
//            ContactInfoComponent.Initializer
//                .init((it.applicationContext as IApp).getAppComponent())
//                .inject(this)
//        }
//        super.onAttach(context)
//    }
}