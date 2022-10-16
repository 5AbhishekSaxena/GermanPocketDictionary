package com.abhishek.germanPocketDictionary.di

import com.abhishek.germanPocketDictionary.data.LocalDataSource
import com.abhishek.germanPocketDictionary.data.WordsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindWordsDataSource(
        localDataSource: LocalDataSource
    ): WordsDataSource

}