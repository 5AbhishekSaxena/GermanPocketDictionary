package com.abhishek.germanPocketDictionary.activity.home.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    HomeContent(
        viewState = viewModel.viewState,
        onPageChange = viewModel::onPageChange,
    )
}