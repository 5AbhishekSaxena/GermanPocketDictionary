package com.abhishek.germanPocketDictionary.activity.feedback.ui.model

data class FeedbackForm(
    val name: String,
    val nameError: String,
    val body: String,
    val bodyError: String,
    val additionalInformation: String
) {
    companion object {
        val Initial = FeedbackForm("", "", "", "", "")
    }
}
