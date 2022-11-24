package com.abhishek.germanPocketDictionary.core.ui.components.words

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun NounExpandableWordListItemContent(plural: String) {
    val formattedPlural = buildAnnotatedString {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Plural: ")
        }
        append(plural)
    }

    Text(
        text = formattedPlural,
        style = MaterialTheme.typography.bodyMedium
    )
}