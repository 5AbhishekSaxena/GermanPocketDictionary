package com.abhishek.germanPocketDictionary.activity.feedback.domain

import com.abhishek.germanPocketDictionary.utilities.Constants

interface DeveloperRepository {
    fun getDeveloperEmailAddresses(): List<String>
}