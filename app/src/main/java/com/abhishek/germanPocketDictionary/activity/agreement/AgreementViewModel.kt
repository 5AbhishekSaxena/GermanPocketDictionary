package com.abhishek.germanPocketDictionary.activity.agreement

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.abhishek.germanPocketDictionary.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class AgreementViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val context: Context
        get() = getApplication<Application>().applicationContext

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

}