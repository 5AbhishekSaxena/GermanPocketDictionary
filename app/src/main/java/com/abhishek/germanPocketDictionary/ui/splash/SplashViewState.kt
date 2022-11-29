package com.abhishek.germanPocketDictionary.ui.splash

sealed class SplashViewState {
    object Initial : SplashViewState()
    object Pending : SplashViewState()
    object Accepted : SplashViewState()
}