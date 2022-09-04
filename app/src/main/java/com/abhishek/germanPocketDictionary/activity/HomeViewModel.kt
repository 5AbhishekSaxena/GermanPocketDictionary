package com.abhishek.germanPocketDictionary.activity

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhishek.germanPocketDictionary.data.WordsRepository
import com.abhishek.germanPocketDictionary.model.Word

class HomeViewModel(
    application: Application,
    private val wordsRepository: WordsRepository
) : AndroidViewModel(application) {

    private val context: Context
        get() = getApplication()

    private val _allWords = MutableLiveData<List<Word>>()
    val allWords: LiveData<List<Word>> = _allWords

    fun getWords() {
        _allWords.value = wordsRepository.getWords()
    }

}