package o.lizuro.utils.context

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

fun Context.getAttrColor(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    val theme = this.theme
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

fun Context.getAttrResId(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    val theme = this.theme
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.resourceId
}