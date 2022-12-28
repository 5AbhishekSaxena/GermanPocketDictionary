package com.abhishek.germanPocketDictionary.agreement.utils

import com.abhishek.germanPocketDictionary.core.utils.Constants
import com.abhishek.germanPocketDictionary.core.utils.SharedPreferenceManager
import javax.inject.Inject

class AgreementPreferenceManagerImpl @Inject constructor(
    private val sharedPreferenceManager: SharedPreferenceManager,
) : AgreementPreferenceManager {
    override fun updateAgreementAcceptanceStatus(status: Boolean) {
        sharedPreferenceManager.putBoolean(Constants.API_KEYS.PREF_AGREEMENT_KEY, status)
    }

    override fun getAgreementAcceptanceStatus(): Boolean {
        return sharedPreferenceManager.getBoolean(Constants.API_KEYS.PREF_AGREEMENT_KEY)
    }
}