package com.abhishek.germanPocketDictionary.activity.feedback.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhishek.germanPocketDictionary.activity.feedback.domain.DeveloperRepository
import com.abhishek.germanPocketDictionary.activity.feedback.domain.Email
import com.abhishek.germanPocketDictionary.utilities.Event

class FeedbackViewModel(
    private val developerRepository: DeveloperRepository
) : ViewModel() {

    private val _usernameError = MutableLiveData<String>()
    val usernameError: LiveData<String> = _usernameError

    private val _feedbackError = MutableLiveData<String>()
    val feedbackError: LiveData<String> = _feedbackError

    private val _sendEmailEvent = MutableLiveData<Event<Email>>()
    val sendEmailEvent: LiveData<Event<Email>> = _sendEmailEvent

    fun onSendFeedback(username: String?, feedback: String?, additionalInformation: String?) {
        when {
            username.isNullOrBlank() -> _usernameError.value = "Username is required"
            feedback.isNullOrBlank() -> _feedbackError.value = "Feedback is required"
            else -> sendFeedback(
                username = username.trim { it <= ' ' },
                feedback = feedback.trim { it <= ' ' },
                additionalInformation = additionalInformation?.trim { it <= ' ' })
        }
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