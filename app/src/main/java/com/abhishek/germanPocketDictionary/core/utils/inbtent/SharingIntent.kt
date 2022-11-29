package com.abhishek.germanPocketDictionary.core.utils.inbtent

import android.content.Intent

interface SharingIntent {
    val intentChooserTitle: String
    fun prepareIntent(): Intent
}