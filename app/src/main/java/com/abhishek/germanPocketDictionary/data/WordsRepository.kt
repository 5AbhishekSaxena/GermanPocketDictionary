package com.abhishek.germanPocketDictionary.data

import com.abhishek.germanPocketDictionary.model.Word
import java.util.regex.Pattern
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val wordsDataSource: WordsDataSource
) {

    private val words = mutableListOf<Word>()

    fun getWords(): List<Word> {
        words.clear()
        words.addAll(wordsDataSource.getWords())
        return words
    }

    fun getWordsByCategory(category: String): List<Word> {
        return wordsDataSource.getWordsByCategory(category)
    }

    fun filterWords(query: String): List<Word> {
        return words.filter {
            val literalPattern = Pattern.quote(query)
            val flags = Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE

            val pattern = Pattern.compile(literalPattern, flags)

            val matcher = pattern.matcher(it.toString())
            matcher.find()
        }
    }
}