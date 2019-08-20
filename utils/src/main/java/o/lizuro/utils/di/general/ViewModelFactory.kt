package o.lizuro.utils.di.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

//@Singleton
//class ViewModelFactory @Inject constructor(
//    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
//}

class ViewModelFactory<T : ViewModel> @Inject constructor(private val provider: Provider<T>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = provider.get() as T
}