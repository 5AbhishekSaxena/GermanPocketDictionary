package com.abhishek.germanPocketDictionary.activity.feedback.domain

import com.abhishek.germanPocketDictionary.utilities.Constants

class ProdDeveloperRepository : DeveloperRepository {
    override fun getDeveloperEmailAddresses(): List<String> = listOf(Constants.DEV_MAIL)
}