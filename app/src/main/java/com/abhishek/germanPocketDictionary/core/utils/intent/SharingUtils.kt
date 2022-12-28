package com.abhishek.germanPocketDictionary.core.utils.intent

import android.content.Context
import android.content.Intent

fun shareIntent(context: Context, sharingIntent: SharingIntent) {
    val intent = sharingIntent.prepareIntent()
    val shareIntent = Intent.createChooser(intent, sharingIntent.intentChooserTitle)
    context.startActivity(shareIntent)
}

fun shareSimpleText(context: Context, text: String) {
    val chooserTitle = "Sharing with"
    val textSharingIntent = DefaultSharingIntent(context, text, chooserTitle)
    shareIntent(context, textSharingIntent)
}

fun openWebPage(
    context: Context,
    url: String,
    chooserTitle: String = "Open url using"
) {
    val webBrowserSharingIntent = WebBrowserSharingIntent(url, chooserTitle)
    shareIntent(context, webBrowserSharingIntent)
}