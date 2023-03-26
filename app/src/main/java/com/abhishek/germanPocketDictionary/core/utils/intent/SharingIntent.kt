package com.abhishek.germanPocketDictionary.core.utils.intent

import android.content.Intent

interface SharingIntent {
    val intentChooserTitle: String
    fun prepareIntent(): Intent
}
