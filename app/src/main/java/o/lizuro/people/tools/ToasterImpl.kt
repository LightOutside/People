package o.lizuro.people.tools

import android.widget.Toast
import o.lizuro.core.IApp
import o.lizuro.core.tools.IToaster
import javax.inject.Inject

class ToasterImpl @Inject constructor(
    private val app: IApp
) : IToaster {
    override fun show(msg: String) {
        Toast.makeText(app.getApplicationContext(), msg, Toast.LENGTH_SHORT).show()
    }
}