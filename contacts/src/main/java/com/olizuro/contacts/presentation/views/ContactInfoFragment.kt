package com.olizuro.contacts.presentation.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.olizuro.contacts.R
import com.olizuro.contacts.di.ContactInfoComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import o.lizuro.core.IApp
import o.lizuro.core.contacts.IContactInfoViewModel
import o.lizuro.core.tools.IErrorHandler
import o.lizuro.coreui.views.BaseFragment
import o.lizuro.utils.rx.storeToComposite
import java.text.SimpleDateFormat
import javax.inject.Inject

class ContactInfoFragment : BaseFragment() {

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

    @Inject
    lateinit var viewModel: IContactInfoViewModel

    @Inject
    lateinit var errorHandler: IErrorHandler

    private lateinit var toolbar: Toolbar
    private lateinit var name: AppCompatTextView
    private lateinit var phone: AppCompatTextView
    private lateinit var temperament: AppCompatTextView
    private lateinit var educationPeriod: AppCompatTextView
    private lateinit var biography: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_info, container, false).apply {
            toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    activity?.run {
                        onBackPressed()
                    }
                }
            }

            name = findViewById(R.id.name)
            phone = findViewById<AppCompatTextView>(R.id.phone).apply {
                setOnClickListener { viewModel.showDialer(context) }
            }
            temperament = findViewById(R.id.temperament)
            educationPeriod = findViewById(R.id.education_period)
            biography = findViewById(R.id.biography)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setContactId(
            arguments?.getString(BUNDLE_CONTACT_ID)
                ?: throw IllegalArgumentException("Missing [contactId] argument for ContactInfoFragment")
        )
    }

    override fun onStart() {
        super.onStart()

        viewModel.getContact()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    name.text = it.name
                    phone.text = it.phone
                    temperament.text = it.temperament.value.capitalize()

                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    val formatter = SimpleDateFormat("dd.MM.yyyy")
                    educationPeriod.text =
                        "${formatter.format(parser.parse(it.educationPeriod.start))} - ${formatter.format(parser.parse(it.educationPeriod.end))}"

                    biography.text = it.biography
                },
                {
                    errorHandler.handleError(it)
                }
            ).storeToComposite(onStartSubscriptions)
    }

    override fun onAttach(context: Context) {
        activity?.let {
            ContactInfoComponent.Initializer
                .init((it.applicationContext as IApp).getAppComponent())
                .inject(this)
        }
        super.onAttach(context)
    }
}