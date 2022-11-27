package com.abhishek.germanPocketDictionary.core.data

import android.content.Context
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.model.Word
import com.abhishek.germanPocketDictionary.utilities.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import java.lang.reflect.Type
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : WordsDataSource {

    private val words: MutableList<Word> = mutableListOf()

    override suspend fun getWords(): List<Word> {
        if (words.isEmpty()) {
            fetchWordsFromJsonAndUpdateCache()
        }
        return words.sortedBy { it.germanTranslationWithoutArticle }
    }

    private fun fetchWordsFromJsonAndUpdateCache() {
        val wordsJson = readRawWordsJson()
        val wordsFromJson = wordsJsonToList(wordsJson)
        words.addAll(wordsFromJson)
    }

    private fun readRawWordsJson(): String {
        val wordsInputStream = context.resources.openRawResource(R.raw.words)
        return getWordsJson(wordsInputStream)
    }

    private fun getWordsJson(wordsInputStream: InputStream): String {
        val wordsBufferedReader = wordsInputStream.bufferedReader()
        val jsonBuilder = StringBuilder()
        wordsBufferedReader.use {
            jsonBuilder.append(it.readText())
        }
        return jsonBuilder.toString()
    }

    private fun wordsJsonToList(wordsJson: String): List<Word> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Word>>() {}.type
        return gson.fromJson(wordsJson, type)
    }

    override suspend fun getWordsByCategory(category: String): List<Word> =
        withContext(Dispatchers.IO) {
            if (words.isEmpty()) {
                fetchWordsFromJsonAndUpdateCache()
            }
            delay(DELAY_TIME)

            if (category == Constants.TABLES.ALL_WORDS) return@withContext words

            if (category == Constants.API_KEYS.CATEGORY_OPPOSITE) {
                return@withContext getOpposites()
            }

            val filteredWords = words.filter { it.category == category }

            /*return*/ if (category == Constants.API_KEYS.CATEGORY_NUMBERS)
            filteredWords.sortedBy { it.numberValue }
        else
            filteredWords.sortedBy { it.germanTranslationWithoutArticle }
    }

    private fun getOpposites(): List<Word> {
        val filteredWords = mutableListOf<Word>()
        val opposites = mutableSetOf<String>()

        words.forEach {
            if (it.hasOpposite() && !opposites.contains(it.germanOpposite)) {
                filteredWords.add(it)
                opposites.add(it.germanTranslation)
            }
        }

        return filteredWords
    }

    companion object {
        private const val DELAY_TIME = 800L
    }
}