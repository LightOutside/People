package o.lizuro.people.tools

import android.content.Context
import o.lizuro.core.tools.IPreferences
import javax.inject.Inject

class PreferencesImpl @Inject constructor(
    private val appContext: Context
) : IPreferences {
    companion object {
        private const val PREFERENCES_NAME = "PeoplePrefs"
    }

    override fun saveLong(key: String, value: Long) {
        appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit().apply {
            putLong(key, value)
            apply()
        }
    }

    override fun loadLong(key: String, defaultValue: Long): Long {
        return appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).getLong(key, defaultValue)
    }
}