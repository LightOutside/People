package o.lizuro.people.tools

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import o.lizuro.core.IApp
import o.lizuro.core.tools.INotifier
import o.lizuro.core.tools.ISystemNavigator
import javax.inject.Inject

class SystemNavigatorImpl @Inject constructor(
    private val app: IApp,
    private val notifier: INotifier
) : ISystemNavigator {

    override fun showDialer(phone: String?) {
        tryRunIntent(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)))
    }

    private fun tryRunIntent(intent: Intent) {
        val appContext = app.getApplicationContext()
        val packageManager = appContext.packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.size > 0) {
            appContext.startActivity(intent)
        } else {
            notifier.notify("На устройстве отсутствует приложение, подходящее для выполнения данной задачи")
        }
    }
}