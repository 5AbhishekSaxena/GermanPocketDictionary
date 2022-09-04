package com.abhishek.germanPocketDictionary.activity

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhishek.germanPocketDictionary.data.LocalDataSource
import com.abhishek.germanPocketDictionary.data.WordsRepository
import com.abhishek.germanPocketDictionary.model.Word

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val context: Context
        get() = getApplication()

    private val _allWords = MutableLiveData<List<Word>>()
    val allWords: LiveData<List<Word>> = _allWords

    fun getWords() {
        val wordsDataSource = LocalDataSource(context)
        val wordsRepository = WordsRepository(wordsDataSource)
        _allWords.value = wordsRepository.getWords()
    }

}