package com.rocky.mvvm.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

private const val KEY_SAVED = "Key_saved"

class PreferenceProvider(
  context: Context
) {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveLastSessionTimeStamp(timeStamp: String) {
        preferences.edit().putString(KEY_SAVED, timeStamp).apply()
    }

    fun getLastTimeStamp(): String? {
        return preferences.getString(KEY_SAVED, null)
    }

}