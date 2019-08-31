package o.lizuro.coreui.views.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseFragment<V : Any> : Fragment() {
    @Inject
    lateinit var viewModel: V

    protected val onStartSubscriptions = CompositeDisposable()
    protected val onCreateSubscriptions = CompositeDisposable()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onStop() {
        super.onStop()
        onStartSubscriptions.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        onCreateSubscriptions.clear()
    }
}