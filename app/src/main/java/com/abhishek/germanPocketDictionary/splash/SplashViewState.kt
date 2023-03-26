package com.abhishek.germanPocketDictionary.splash

sealed class SplashViewState {
    object Initial : SplashViewState()
    object Pending : SplashViewState()
    object Accepted : SplashViewState()
}
