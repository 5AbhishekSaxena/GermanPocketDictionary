package com.abhishek.germanPocketDictionary.agreement.utils

interface AgreementPreferenceManager {
    fun updateAgreementAcceptanceStatus(status: Boolean)
    fun getAgreementAcceptanceStatus(): Boolean
}