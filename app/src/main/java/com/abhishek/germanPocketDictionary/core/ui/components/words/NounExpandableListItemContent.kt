package com.abhishek.germanPocketDictionary.core.ui.components.words

import androidx.compose.runtime.Composable

@Composable
fun NounExpandableWordListItemContent(plural: String) {
    LabelAndText(label = "Plural", text = plural)
}