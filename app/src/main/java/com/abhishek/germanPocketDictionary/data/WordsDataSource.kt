package com.abhishek.germanPocketDictionary.data

import com.abhishek.germanPocketDictionary.model.Word

interface WordsDataSource {

    suspend fun getWords(): List<Word>
    suspend fun getWordsByCategory(category: String): List<Word>
}
