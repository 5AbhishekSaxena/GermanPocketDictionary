package com.abhishek.germanPocketDictionary.activity.feedback.ui

import com.abhishek.germanPocketDictionary.activity.feedback.ui.model.FeedbackForm

sealed class FeedbackViewState(
    open val feedbackForm: FeedbackForm,
    open val inputEnabled: Boolean = true
) {
    object Initial : FeedbackViewState(FeedbackForm.Initial)

    data class Active(
        override val feedbackForm: FeedbackForm
    ) : FeedbackViewState(feedbackForm)

    data class Submitting(
        override val feedbackForm: FeedbackForm
    ) : FeedbackViewState(feedbackForm, inputEnabled = false)

    data class SubmissionError(
        override val feedbackForm: FeedbackForm
    ) : FeedbackViewState(feedbackForm)

    data class Complete(override val feedbackForm: FeedbackForm) :
        FeedbackViewState(feedbackForm, inputEnabled = false)

}
