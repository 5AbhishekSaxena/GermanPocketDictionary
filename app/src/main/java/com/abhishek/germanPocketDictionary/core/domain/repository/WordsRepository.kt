package com.abhishek.germanPocketDictionary.core.domain.repository

import com.abhishek.germanPocketDictionary.core.domain.model.Word
import java.util.regex.Pattern
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val wordsDataSource: WordsDataSource,
) {

    private val words = mutableListOf<Word>()

    suspend fun getWords(): List<Word> {
        words.clear()
        words.addAll(wordsDataSource.getWords())
        return words
    }

    suspend fun getWordsByCategory(category: String): List<Word> {
        return wordsDataSource.getWordsByCategory(category)
    }

    fun filterWords(query: String): List<Word> {
        if (query.isBlank()) return words

        val sanitizedQuery = query.trim()

        return words.filter {
            val literalPattern = Pattern.quote(sanitizedQuery)
            val flags = Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE

            val pattern = Pattern.compile(literalPattern, flags)

            val matcher = pattern.matcher(it.toString())
            matcher.find()
        }
    }
}
