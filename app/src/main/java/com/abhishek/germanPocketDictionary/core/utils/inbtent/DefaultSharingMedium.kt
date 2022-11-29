package com.abhishek.germanPocketDictionary.core.utils.inbtent

import android.content.Context
import android.content.Intent
import androidx.core.app.ShareCompat

class DefaultSharingIntent(
    private val context: Context,
    private val body: String,
    chooserTitle: String = "Share using",
) : SharingIntent {
    override val intentChooserTitle: String = chooserTitle

    override fun prepareIntent(): Intent {
        return ShareCompat.IntentBuilder(context)
            .setType("text/plain")
            .setText(body)
            .intent
    }
}