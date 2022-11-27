package com.abhishek.germanPocketDictionary.activity.agreement.utils

interface AgreementPreferenceManager {
    fun updateAgreementAcceptanceStatus(status: Boolean)
    fun getAgreementAcceptanceStatus(): Boolean
}