package com.abhishek.germanPocketDictionary.activity.splash

sealed class SplashViewState {
    object Initial : SplashViewState()
    object Pending : SplashViewState()
    object Accepted : SplashViewState()
}