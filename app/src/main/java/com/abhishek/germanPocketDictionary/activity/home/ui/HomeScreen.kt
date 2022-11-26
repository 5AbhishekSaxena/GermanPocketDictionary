package com.abhishek.germanPocketDictionary.activity.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onSearchClick: () -> Unit,
    onRateThisAppClick: () -> Unit,
) {

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
        onSearchClick = onSearchClick,
        onRateThisAppClick = onRateThisAppClick,
    )
}