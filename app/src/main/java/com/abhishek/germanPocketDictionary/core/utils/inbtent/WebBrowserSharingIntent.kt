package com.abhishek.germanPocketDictionary.core.utils.inbtent

import android.content.Intent
import androidx.core.net.toUri

class WebBrowserSharingIntent(
    private val url: String,
    chooserTitle: String = "Please select the web browser",
) : SharingIntent {

    override val intentChooserTitle: String = chooserTitle

    override fun prepareIntent(): Intent {
        return Intent().apply {
            action = Intent.ACTION_VIEW
            data = url.toUri()
        }
    }
}