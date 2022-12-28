package com.abhishek.germanPocketDictionary.home.ui

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