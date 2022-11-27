package com.abhishek.germanPocketDictionary.activity.home.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhishek.germanPocketDictionary.activity.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val allWordsPageViewState by viewModel.allWordsPageViewState.collectAsStateWithLifecycle()
    val nounsPageViewState by viewModel.nounsPageViewState.collectAsStateWithLifecycle()
    val verbsPageViewState by viewModel.verbsPageViewState.collectAsStateWithLifecycle()
    val numbersPageViewState by viewModel.numbersPageViewState.collectAsStateWithLifecycle()
    val colorsPageViewState by viewModel.colorsPageViewState.collectAsStateWithLifecycle()
    val questionsPageViewState by viewModel.questionsPageViewState.collectAsStateWithLifecycle()
    val oppositesPageViewState by viewModel.oppositesPageViewState.collectAsStateWithLifecycle()

    HomeContent(
        allWordsPageViewState = allWordsPageViewState,
        nounsPageViewState = nounsPageViewState,
        verbsPageViewState = verbsPageViewState,
        numbersPageViewState = numbersPageViewState,
        colorsPageViewState = colorsPageViewState,
        questionsPageViewState = questionsPageViewState,
        oppositesPageViewState = oppositesPageViewState,
        onPageChange = viewModel::onPageChange,
        onSearchClick = {
            navigator.navigate(SearchScreenDestination)
        },
        onRateThisAppClick = {
            onRateThisAppClick(context)
        },
    )
}


private fun onRateThisAppClick(context: Context) {
    val playStoreAppUri =
        Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
    val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreAppUri)
    Intent.createChooser(playStoreIntent, "Please select to rate the app.").also {
        context.startActivity(it)
    }
}