package com.abhishek.germanPocketDictionary.data

import com.abhishek.germanPocketDictionary.model.Word

interface WordsDataSource {

    fun getWords(): List<Word>
}