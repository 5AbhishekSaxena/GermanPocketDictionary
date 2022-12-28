package com.abhishek.germanPocketDictionary.agreement.domain.repository

interface AgreementRepository {
    fun getAgreement(): String
    fun updateAgreementAcceptanceStatus(status: Boolean)
    fun getAgreementAcceptanceStatus(): Boolean
}