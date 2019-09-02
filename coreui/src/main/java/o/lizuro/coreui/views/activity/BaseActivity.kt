package o.lizuro.coreui.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseActivity<V: Any> : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModel: V

    protected val onStartSubscriptions = CompositeDisposable()
    protected val onCreateSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        onStartSubscriptions.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        onCreateSubscriptions.clear()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}