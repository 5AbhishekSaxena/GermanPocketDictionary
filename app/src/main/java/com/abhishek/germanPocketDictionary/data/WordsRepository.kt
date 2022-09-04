package com.abhishek.germanPocketDictionary.data

import com.abhishek.germanPocketDictionary.model.Word

class WordsRepository(
    private val wordsDataSource: WordsDataSource
) {

    fun getWords(): List<Word> {
        return wordsDataSource.getWords()
    }
}