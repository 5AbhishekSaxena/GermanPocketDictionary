package com.abhishek.germanPocketDictionary.activity.feedback.domain

data class Email(
    val receiverEmailAddresses: List<String>,
    val subject: String,
    val message: String
) {
    constructor(
        receiverEmail: String,
        subject: String,
        message: String
    ) : this(listOf(receiverEmail), subject, message)
}