package o.lizuro.people.tools

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import o.lizuro.core.IApp
import o.lizuro.core.tools.INetworkChecker
import javax.inject.Inject

class NetworkCheckerImpl @Inject constructor(
    private val app: IApp
) : INetworkChecker {
    override fun isOnline(): Boolean {
        return getSystemService(app.getApplicationContext(), ConnectivityManager::class.java)?.activeNetworkInfo?.isConnected ?: false
    }
}