package o.lizuro.people.tools

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import o.lizuro.core.tools.INetworkChecker
import javax.inject.Inject

class NetworkCheckerImpl @Inject constructor(
    private val appContext: Context
) : INetworkChecker {
    override fun isOnline(): Boolean {
        return getSystemService(appContext, ConnectivityManager::class.java)?.activeNetworkInfo?.isConnected ?: false
    }
}