package com.abhishek.germanPocketDictionary.activity.agreement.domain.repository

interface AgreementRepository {
    fun getAgreement(): String
    fun updateAgreementAcceptanceStatus(status: Boolean)
    fun getAgreementAcceptanceStatus(): Boolean
}