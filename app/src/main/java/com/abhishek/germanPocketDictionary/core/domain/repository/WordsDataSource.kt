package com.abhishek.germanPocketDictionary.core.domain.repository

import com.abhishek.germanPocketDictionary.core.domain.model.Word

interface WordsDataSource {

    suspend fun getWords(): List<Word>
    suspend fun getWordsByCategory(category: String): List<Word>
}
