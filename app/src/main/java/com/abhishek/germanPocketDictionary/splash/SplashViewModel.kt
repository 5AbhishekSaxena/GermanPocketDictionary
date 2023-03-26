package com.abhishek.germanPocketDictionary.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.germanPocketDictionary.agreement.domain.repository.AgreementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val agreementRepository: AgreementRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<SplashViewState>(SplashViewState.Initial)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val accepted = agreementRepository.getAgreementAcceptanceStatus()
            delay(500L)
            _viewState.value = if (accepted) {
                SplashViewState.Accepted
            } else {
                SplashViewState.Pending
            }
        }
    }
}
