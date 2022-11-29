package com.abhishek.germanPocketDictionary.core.ui.components.words

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun GermanWordAndTranslation(
    germanTranslation: String,
    englishTranslation: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = germanTranslation,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = englishTranslation,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
