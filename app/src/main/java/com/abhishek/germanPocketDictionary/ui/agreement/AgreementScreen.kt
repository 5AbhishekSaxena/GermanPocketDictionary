package com.abhishek.germanPocketDictionary.ui.agreement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhishek.germanPocketDictionary.ui.destinations.AgreementScreenDestination
import com.abhishek.germanPocketDictionary.ui.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Composable
@Destination
fun AgreementScreen(
    navigator: DestinationsNavigator,
    viewModel: AgreementViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewState) {
        if (viewState is AgreementViewState.Loaded.Accepted) {
            navigator.navigate(HomeScreenDestination) {
                popUpTo(AgreementScreenDestination) {
                    inclusive = true
                }
            }
        }
    }

    AgreementContent(
        viewState = viewState,
        onShowAgreementButtonClick = viewModel::onShowAgreementButtonClick,
        onDialogConfirmButtonClick = viewModel::onDialogConfirmButtonClick,
        onDialogDismissButtonClick = viewModel::onDialogDismissButtonClick,
        onTermsAcceptedCheckedChange = viewModel::onTermsAcceptedCheckedChange
    )
}