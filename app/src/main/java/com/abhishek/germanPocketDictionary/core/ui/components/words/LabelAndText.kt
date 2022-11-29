package com.abhishek.germanPocketDictionary.core.ui.components.words

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun LabelAndText(
    label: String,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    val formattedPlural = buildAnnotatedString {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$label: ")
        }
        append(text)
    }

    Text(
        text = formattedPlural,
        style = style,
    )
}