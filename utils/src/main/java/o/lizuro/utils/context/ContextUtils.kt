package o.lizuro.utils.context

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

fun Context.getAttrColor(@AttrRes attr: Int): Int {
    return TypedValue().apply { theme.resolveAttribute(attr, this, true) }.data
}

fun Context.getAttrResId(@AttrRes attr: Int): Int {
    return TypedValue().apply { theme.resolveAttribute(attr, this, true) }.resourceId
}