package o.lizuro.people.tools

import android.content.Context
import android.widget.Toast
import o.lizuro.core.tools.IToaster
import javax.inject.Inject

class ToasterImpl @Inject constructor(
    private val appContext: Context
) : IToaster {
    override fun show(msg: String) {
        Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show()
    }
}