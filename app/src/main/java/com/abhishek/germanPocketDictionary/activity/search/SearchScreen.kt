package com.abhishek.germanPocketDictionary.activity.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onNavigateIconClick: () -> Unit,
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    SearchContent(
        viewState = viewState,
        onQueryChange = viewModel::filterWords,
        onNavigateIconClick = onNavigateIconClick,
    )
}