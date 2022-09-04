package com.abhishek.germanPocketDictionary.data

import android.content.Context
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.model.Word
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import java.lang.reflect.Type
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : WordsDataSource {

    override fun getWords(): List<Word> {
        val wordsJson = readRawWordsJson()
        return wordsJsonToList(wordsJson)
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
}