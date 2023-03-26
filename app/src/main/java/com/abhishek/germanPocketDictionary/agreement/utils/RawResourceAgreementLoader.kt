package com.abhishek.germanPocketDictionary.agreement.utils

import android.content.Context
import com.abhishek.germanPocketDictionary.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class RawResourceAgreementLoader @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : AgreementLoader {
    override fun loadAgreement(): String {
        val agreement = StringBuilder()
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
        return agreement.toString()
    }
}
