package com.abhishek.germanPocketDictionary.ui.home.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhishek.germanPocketDictionary.core.utils.intent.openWebPage
import com.abhishek.germanPocketDictionary.core.utils.intent.shareSimpleText
import com.abhishek.germanPocketDictionary.ui.destinations.SearchScreenDestination
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
        onShareClick = {
            onShareClick(context)
        },
    )
}

private fun onRateThisAppClick(context: Context) {
    val url = "https://play.google.com/store/apps/details?id=${context.packageName}"

    openWebPage(
        context = context,
        url = url,
        chooserTitle = "Rate the app using"
    )
}

private fun onShareClick(context: Context) {
    val url = "https://play.google.com/store/apps/details?id=${context.packageName}"
    val message = """
        |Are you looking for an amazing yet so simple dictionary to help you with German-English translations?
        |Get it at $url 
    """.trimMargin()

    shareSimpleText(context = context, text = message)
}