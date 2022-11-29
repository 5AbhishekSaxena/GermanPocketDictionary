package com.abhishek.germanPocketDictionary.ui.agreement.utils

interface AgreementPreferenceManager {
    fun updateAgreementAcceptanceStatus(status: Boolean)
    fun getAgreementAcceptanceStatus(): Boolean
}