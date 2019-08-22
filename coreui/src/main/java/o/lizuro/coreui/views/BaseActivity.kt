package o.lizuro.coreui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

open class BaseActivity<V: Any> : AppCompatActivity() {
    @Inject
    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}

/*
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
 */