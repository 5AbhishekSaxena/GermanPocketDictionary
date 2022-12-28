package com.abhishek.germanPocketDictionary.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptionsBuilder
import com.abhishek.germanPocketDictionary.destinations.AgreementScreenDestination
import com.abhishek.germanPocketDictionary.destinations.HomeScreenDestination
import com.abhishek.germanPocketDictionary.destinations.SplashScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Composable
@Destination
@RootNavGraph(start = true)
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: SplashViewModel = hiltViewModel(),
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewState) {
        val builder: NavOptionsBuilder.() -> Unit = {
            popUpTo(SplashScreenDestination) {
                inclusive = true
            }
        }

        if (viewState is SplashViewState.Pending)
            navigator.navigate(AgreementScreenDestination, builder = builder)
        else if (viewState is SplashViewState.Accepted)
            navigator.navigate(HomeScreenDestination, builder = builder)
    }

    SplashContent()

}