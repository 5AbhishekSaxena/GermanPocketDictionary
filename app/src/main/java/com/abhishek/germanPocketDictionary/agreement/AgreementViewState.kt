package com.abhishek.germanPocketDictionary.agreement

sealed class AgreementViewState {
    object Initial : AgreementViewState()

    object Loading : AgreementViewState()

    sealed class Loaded(
        open val showDialog: Boolean,
        open val status: Boolean,
        open val agreement: String,
        open val termsAccepted: Boolean = false,
        val inputEnabled: Boolean = false
    ) : AgreementViewState() {

        data class Active(
            override val showDialog: Boolean,
            override val status: Boolean,
            override val agreement: String,
            override val termsAccepted: Boolean,
        ) : Loaded(
            showDialog = showDialog,
            status = status,
            agreement = agreement,
            termsAccepted = termsAccepted,
            inputEnabled = true,
        )

        data class Submitting(
            override val status: Boolean,
            override val agreement: String,
//            override val termsAccepted: Boolean,
        ) : Loaded(
            showDialog = true,
            status = status,
            agreement = agreement,
            termsAccepted = true,
            inputEnabled = false
        )

        data class Accepted(
            override val status: Boolean,
            override val agreement: String
        ) : Loaded(
            showDialog = false,
            status = status,
            agreement = agreement,
            termsAccepted = true,
            inputEnabled = true
        )
    }

    data class Error(val error: Exception) : AgreementViewState()

}
