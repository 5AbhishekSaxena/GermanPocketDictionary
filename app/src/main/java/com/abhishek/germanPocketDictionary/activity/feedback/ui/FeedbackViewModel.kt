package com.abhishek.germanPocketDictionary.activity.feedback.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.activity.feedback.domain.DeveloperRepository
import com.abhishek.germanPocketDictionary.activity.feedback.domain.Email
import com.abhishek.germanPocketDictionary.activity.feedback.ui.model.FeedbackForm
import com.abhishek.germanPocketDictionary.core.ui.UiText
import com.abhishek.germanPocketDictionary.utilities.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FeedbackViewModel(
    private val developerRepository: DeveloperRepository
) : ViewModel() {

    private val _usernameError = MutableLiveData<String>()
    val usernameError: LiveData<String> = _usernameError

    private val _feedbackError = MutableLiveData<String>()
    val feedbackError: LiveData<String> = _feedbackError

    private val _sendEmailEvent = MutableLiveData<Event<Email>>()
    val sendEmailEvent: LiveData<Event<Email>> = _sendEmailEvent

    private val _viewState = MutableStateFlow<FeedbackViewState>(FeedbackViewState.Initial)
    val viewState = _viewState.asStateFlow()

    fun onNameChange(name: String) {
        _viewState.update {
            val updatedFeedbackForm = it.feedbackForm.withName(name)

            if (it is FeedbackViewState.Active)
                it.copy(feedbackForm = updatedFeedbackForm)
            else
                FeedbackViewState.Active(feedbackForm = updatedFeedbackForm)
        }
    }

    fun onBodyChange(body: String) {
        _viewState.update {
            val updatedFeedbackForm = it.feedbackForm.withBody(body)

            if (it is FeedbackViewState.Active)
                it.copy(feedbackForm = updatedFeedbackForm)
            else
                FeedbackViewState.Active(feedbackForm = updatedFeedbackForm)
        }
    }

    fun onAdditionalInformationChange(additionalInformation: String) {
        _viewState.update {
            val updatedFeedbackForm = it.feedbackForm
                .withAdditionalInformation(additionalInformation)

            if (it is FeedbackViewState.Active)
                it.copy(feedbackForm = updatedFeedbackForm)
            else
                FeedbackViewState.Active(feedbackForm = updatedFeedbackForm)
        }

    }

    fun onSubmit() {
        val currentViewState = _viewState.value

        _viewState.value = FeedbackViewState.Submitting(currentViewState.feedbackForm)

        val name = currentViewState.feedbackForm.name
        val body = currentViewState.feedbackForm.body
        val additionalInformation = currentViewState.feedbackForm.additionalInformation

        val nameError = name.isBlank()
        val bodyError = body.isBlank()

        if (nameError || bodyError) {
            _viewState.value = FeedbackViewState.SubmissionError(
                currentViewState.feedbackForm.copy(
                    nameError = UiText.ResourceText(R.string.error_name_empty)
                        .takeIf { nameError },
                    bodyError = UiText.ResourceText(R.string.error_feedback_empty)
                        .takeIf { bodyError }
                ),
            )
            return
        }

        sendFeedback(
            username = name.trim(),
            feedback = body.trim(),
            additionalInformation = additionalInformation.trim(),
        )
    }

    private fun sendFeedback(username: String, feedback: String, additionalInformation: String?) {
        val email = composeEmail(username, feedback, additionalInformation)
        _sendEmailEvent.value = Event(email)
    }

    private fun composeEmail(
        username: String,
        feedback: String,
        additionalInformation: String?
    ): Email {
        val receiverEmailAddress = developerRepository.getDeveloperEmailAddresses()
        val subject = "Feedback - $username"
        val body = composeBody(username, feedback, additionalInformation)
        return Email(
            receiverEmailAddresses = receiverEmailAddress,
            subject = subject,
            message = body
        )
    }

    private fun composeBody(
        username: String,
        feedback: String,
        additionalInformation: String?
    ): String {
        return buildString {
            append("Greetings,")
            append("\n")
            append("\n")
            append("I would like to send a feedback after using your application.")
            append("\n")
            append("\n")
            append("Feedback:")
            append("\n")
            append(feedback)
            if (additionalInformation != null && additionalInformation.isNotEmpty()) {
                append("\n")
                append("\n")
                append("Additional Information: ")
                append(additionalInformation)
            }
            append("\n")
            append("\n")
            append("Best Regards")
            append("\n")
            append(username)
        }
    }
}

private fun FeedbackForm.withName(name: String): FeedbackForm {
    return this.copy(name = name, nameError = null)
}

private fun FeedbackForm.withBody(body: String): FeedbackForm {
    return this.copy(body = body, bodyError = null)
}

private fun FeedbackForm.withAdditionalInformation(additionalInformation: String): FeedbackForm {
    return this.copy(additionalInformation = additionalInformation)
}