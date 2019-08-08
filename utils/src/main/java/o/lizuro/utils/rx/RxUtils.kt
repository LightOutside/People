package o.lizuro.utils.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.storeToComposite(composite: CompositeDisposable) {
    composite.add(this)
}