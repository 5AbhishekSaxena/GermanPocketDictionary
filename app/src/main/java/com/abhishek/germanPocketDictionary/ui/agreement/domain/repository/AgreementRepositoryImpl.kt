package com.abhishek.germanPocketDictionary.ui.agreement.domain.repository

import com.abhishek.germanPocketDictionary.ui.agreement.utils.AgreementLoader
import com.abhishek.germanPocketDictionary.ui.agreement.utils.AgreementPreferenceManager
import javax.inject.Inject

class AgreementRepositoryImpl @Inject constructor(
    private val agreementLoader: AgreementLoader,
    private val agreementPreferenceManager: AgreementPreferenceManager
) : AgreementRepository {

    private var agreement: String? = null

    override fun getAgreement(): String {
        return agreement ?: agreementLoader.loadAgreement().also {
            agreement = it
        }
    }

    override fun updateAgreementAcceptanceStatus(status: Boolean) {
        agreementPreferenceManager.updateAgreementAcceptanceStatus(status)
    }

    override fun getAgreementAcceptanceStatus(): Boolean {
        return agreementPreferenceManager.getAgreementAcceptanceStatus()
    }
}