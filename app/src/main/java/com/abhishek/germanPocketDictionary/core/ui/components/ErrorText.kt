package com.abhishek.germanPocketDictionary.core.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorText(
    exception: Exception,
    modifier: Modifier = Modifier,
) {
    Text(
        text = exception.message ?: "Something went wrong!",
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        color = MaterialTheme.colorScheme.error,
    )
}
