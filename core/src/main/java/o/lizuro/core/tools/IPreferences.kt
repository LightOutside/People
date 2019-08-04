package o.lizuro.core.tools

interface IPreferences {
    fun saveLong(key: String, value: Long)
    fun loadLong(key: String, defaultValue: Long) : Long
}