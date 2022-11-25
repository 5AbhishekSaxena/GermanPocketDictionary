package com.abhishek.germanPocketDictionary.activity.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    HomeContent(viewState = viewState)

}