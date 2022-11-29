package com.abhishek.germanPocketDictionary.core.ui.components.words

sealed class UIMinWord(
    open val germanTranslation: String,
    open val englishTranslation: String
) {
    data class Simple(
        override val germanTranslation: String,
        override val englishTranslation: String,
    ) : UIMinWord(germanTranslation = germanTranslation, englishTranslation = englishTranslation)

    data class Noun(
        override val germanTranslation: String,
        override val englishTranslation: String,
        val plural: String,
    ) : UIMinWord(germanTranslation = germanTranslation, englishTranslation = englishTranslation)

    data class Verb(
        override val germanTranslation: String,
        override val englishTranslation: String,
        val partizip: String,
        val helpingVerb: String,
    ) : UIMinWord(germanTranslation = germanTranslation, englishTranslation = englishTranslation)

    data class Opposites(
        override val germanTranslation: String,
        override val englishTranslation: String,
        val oppositeGermanTranslation: String,
        val oppositeEnglishTranslation: String,
    ) : UIMinWord(germanTranslation = germanTranslation, englishTranslation = englishTranslation)

}