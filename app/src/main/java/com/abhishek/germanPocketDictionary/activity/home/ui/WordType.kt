package com.abhishek.germanPocketDictionary.activity.home.ui

enum class WordType(private val simplifiedName: String? = null) {
    ALL_WORDS("ALL WORDS"),
    NOUNS,
    VERBS,
    NUMBERS,
    COLORS,
    QUESTIONS,
    OPPOSITE;

    fun getSimplifiedName(): String {
        return simplifiedName ?: name
    }
}