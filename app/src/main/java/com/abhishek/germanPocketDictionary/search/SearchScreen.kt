package com.abhishek.germanPocketDictionary.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    SearchContent(
        viewState = viewState,
        onQueryChange = viewModel::filterWords,
        onNavigateIconClick = {
            navigator.navigateUp()
        },
    )
}