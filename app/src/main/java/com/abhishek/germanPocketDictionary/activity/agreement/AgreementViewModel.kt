package com.abhishek.germanPocketDictionary.activity.agreement

import androidx.lifecycle.ViewModel
import com.abhishek.germanPocketDictionary.activity.agreement.domain.repository.AgreementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AgreementViewModel @Inject constructor(
    private val agreementRepository: AgreementRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<AgreementViewState>(AgreementViewState.Initial)
    val viewState = _viewState.asStateFlow()

    init {
        loadAgreement()
    }

    private fun loadAgreement() {
        try {
            _viewState.value = AgreementViewState.Loading
            val agreement = agreementRepository.getAgreement()
            _viewState.value = AgreementViewState.Loaded.Active(
                showDialog = true,
                status = false,
                agreement = agreement,
                termsAccepted = false,
            )
        } catch (exception: Exception) {
            _viewState.value = AgreementViewState.Error(exception)
        }
    }

    fun onShowAgreementButtonClick() {
        val currentViewState = _viewState.value
        if (currentViewState is AgreementViewState.Loaded.Active)
            _viewState.value = currentViewState.copy(showDialog = true)
    }

    fun onDialogConfirmButtonClick() {
        val currentViewState = _viewState.value
        if (currentViewState !is AgreementViewState.Loaded.Active || !currentViewState.termsAccepted) return

        onAgreementAcceptanceStatusUpdate(currentViewState, true)

        _viewState.value = AgreementViewState.Loaded.Accepted(
            currentViewState.status,
            currentViewState.agreement,
        )
    }

    fun onDialogDismissButtonClick() {
        val currentViewState = _viewState.value
        if (currentViewState !is AgreementViewState.Loaded.Active || !currentViewState.termsAccepted) return

        onAgreementAcceptanceStatusUpdate(currentViewState, false)

        _viewState.value = AgreementViewState.Loaded.Active(
            showDialog = false,
            status = currentViewState.status,
            agreement = currentViewState.agreement,
            termsAccepted = false,
        )
    }

    private fun onAgreementAcceptanceStatusUpdate(
        currentViewState: AgreementViewState.Loaded.Active,
        status: Boolean
    ) {
        if (!currentViewState.termsAccepted) return

        _viewState.value = AgreementViewState.Loaded.Submitting(
            status = status,
            currentViewState.agreement,
        )

        updateAgreementAcceptanceStatus(status)

    }

    private fun updateAgreementAcceptanceStatus(status: Boolean) {
        val currentViewState = _viewState.value
        if (currentViewState !is AgreementViewState.Loaded.Submitting || !currentViewState.termsAccepted) return
        agreementRepository.updateAgreementAcceptanceStatus(status)
    }

    fun onTermsAcceptedCheckedChange(checked: Boolean) {
        val currentViewState = _viewState.value
        if (currentViewState !is AgreementViewState.Loaded.Active) return

        _viewState.value = currentViewState.copy(termsAccepted = checked)
    }
}