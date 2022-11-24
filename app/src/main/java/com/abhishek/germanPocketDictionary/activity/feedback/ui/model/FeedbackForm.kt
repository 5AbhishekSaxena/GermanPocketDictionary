package com.abhishek.germanPocketDictionary.activity.feedback.ui.model

import com.abhishek.germanPocketDictionary.core.ui.UiText

data class FeedbackForm(
    val name: String,
    val nameError: UiText?,
    val body: String,
    val bodyError: UiText?,
    val additionalInformation: String
) {
    companion object {
        val Initial = FeedbackForm("", null, "", null, "")
    }
}
