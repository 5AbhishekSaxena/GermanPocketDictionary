package com.abhishek.germanPocketDictionary.data

import com.abhishek.germanPocketDictionary.model.Word
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val wordsDataSource: WordsDataSource
) {

    fun getWords(): List<Word> {
        return wordsDataSource.getWords()
    }

    fun getWordsByCategory(category: String): List<Word> {
        return wordsDataSource.getWordsByCategory(category)
    }
}