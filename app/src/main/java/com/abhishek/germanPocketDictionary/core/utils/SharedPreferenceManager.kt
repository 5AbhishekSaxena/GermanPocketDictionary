package com.abhishek.germanPocketDictionary.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    @ApplicationContext
    context: Context,
) {

    private val sharedPreferences: SharedPreferences? =
        PreferenceManager.getDefaultSharedPreferences(context)

    private val editor: SharedPreferences.Editor?
        get() = sharedPreferences?.edit()

    fun putBoolean(key: String, value: Boolean) {
        editor?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences?.getBoolean(key, false) ?: false
    }
}
