package com.abhishek.germanPocketDictionary.activity.feedback.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FeedbackFormScreen(
    viewModel: FeedbackViewModel
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    FeedbackFormContent(
        viewState = viewState,
        onNameChange = viewModel::onNameChange,
        onBodyChange = viewModel::onBodyChange,
        onAdditionalInformationChange = viewModel::onAdditionalInformationChange,
        onSubmit = viewModel::onSubmit
    )
}