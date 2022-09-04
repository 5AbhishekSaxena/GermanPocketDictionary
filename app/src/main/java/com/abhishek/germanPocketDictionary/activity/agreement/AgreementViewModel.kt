package com.abhishek.germanPocketDictionary.activity.agreement

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.utilities.Constants
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class AgreementViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val context: Context
        get() = getApplication<Application>().applicationContext

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private val sharedPreferenceEditor: SharedPreferences.Editor = sharedPreferences.edit()

    fun loadAgreementDetails(): String {
        val agreement = StringBuilder()
        return try {
            val inputStream =
                context.resources.openRawResource(R.raw.terms_of_use_warranties_and_release_agreement)

            val inputStreamReader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line = bufferedReader.readLine()
            while (line != null) {
                agreement.append("\n")
                agreement.append(line)
                line = bufferedReader.readLine()
            }
            agreement.toString()
        } catch (e: Exception) {
            throw IOException()
        }
    }

    fun onAgreementAccepted() {
        updateAgreementAcceptanceStatus(true)
    }

    fun onAgreementDenied() {
        updateAgreementAcceptanceStatus(false)
    }

    private fun updateAgreementAcceptanceStatus(status: Boolean) {
        sharedPreferenceEditor.apply {
            putBoolean(Constants.API_KEYS.PREF_AGREEMENT_KEY, status)
            apply()
        }
    }

    fun checkIfAgreementIsAccepted(): Boolean {
        return sharedPreferences.getBoolean(Constants.API_KEYS.PREF_AGREEMENT_KEY, false)
    }

    fun migrateOldAgreementStatusKeyToNewOneIfPresent() {
        if (sharedPreferences.contains("agreed")) {
            sharedPreferenceEditor.apply {
                remove("agreed")
                apply()
            }
        }
    }
}