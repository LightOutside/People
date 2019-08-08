package o.lizuro.coreui.views

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {
    protected val onStartSubscriptions = CompositeDisposable()
    protected val onCreateSubscriptions = CompositeDisposable()

    override fun onStop() {
        super.onStop()
        onStartSubscriptions.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        onCreateSubscriptions.clear()
    }
}