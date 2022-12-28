package com.abhishek.germanPocketDictionary.core.di

import com.abhishek.germanPocketDictionary.ui.agreement.domain.repository.AgreementRepository
import com.abhishek.germanPocketDictionary.ui.agreement.domain.repository.AgreementRepositoryImpl
import com.abhishek.germanPocketDictionary.ui.agreement.utils.AgreementLoader
import com.abhishek.germanPocketDictionary.ui.agreement.utils.AgreementPreferenceManager
import com.abhishek.germanPocketDictionary.ui.agreement.utils.AgreementPreferenceManagerImpl
import com.abhishek.germanPocketDictionary.ui.agreement.utils.RawResourceAgreementLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AgreementModule {

    @Binds
    @Singleton
    fun bindAgreementLoader(
        agreementLoader: RawResourceAgreementLoader
    ): AgreementLoader

    @Binds
    @Singleton
    fun bindAgreementRepository(
        agreementRepository: AgreementRepositoryImpl
    ): AgreementRepository

    @Binds
    @Singleton
    fun bindAgreementSharedPreferenceManager(
        agreementPreferenceManager: AgreementPreferenceManagerImpl
    ): AgreementPreferenceManager
}