package com.abhishek.germanPocketDictionary.data

import com.abhishek.germanPocketDictionary.model.Word

interface WordsDataSource {

    fun getWords(): List<Word>
    fun getWordsByCategory(category: String): List<Word>
}
