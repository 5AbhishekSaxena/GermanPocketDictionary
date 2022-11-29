package com.abhishek.germanPocketDictionary.core.domain.model;

import static com.abhishek.germanPocketDictionary.core.utils.Constants.API_KEYS;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

@Keep
public class Word implements Serializable {

    private String id;
    private String germanTranslation;
    private String englishTranslation;
    private String germanTranslationWithoutArticle;
    private String category;
    private String germanOpposite;
    private String germanOppositeMeaning;
    private String gender;
    private String germanPlural;
    private int numberValue;
    private String verbRootWord;
    private String verbPartizip;
    private String helpingVerb;

    public Word() {

    }

    public Word(String id, String germanTranslation, String englishTranslation, String germanPlural,
                String category, String numberValue, String verbRootWord, String verbPartizip,
                String helpingVerb, String germanOpposite, String germanOppositeMeaning) {

        this.id = id;

        this.englishTranslation = englishTranslation;

        if (germanPlural.equals("-1"))
            this.germanPlural = "No Plural";
        else if (!germanPlural.equals("0"))
            this.germanPlural = germanPlural.trim();
        else
            this.germanPlural = null;

        if (!verbPartizip.equals("0"))
            this.verbPartizip = verbPartizip.trim();
        else
            this.verbPartizip = null;

        if (!helpingVerb.equals("0"))
            this.helpingVerb = helpingVerb.trim();
        else
            this.helpingVerb = null;

        this.category = category;

        this.numberValue = category.equals("2") ? Integer.valueOf(numberValue) : -1;

        if (germanOpposite.equals("0")) {
            this.germanOpposite = null;
            this.germanOppositeMeaning = null;
        } else {
            this.germanOpposite = germanOpposite.trim();
            this.germanOppositeMeaning = germanOppositeMeaning.trim();
        }

        if (!category.equals("7"))
            this.verbRootWord = null;
        else if (verbRootWord.equals("-1"))
            this.verbRootWord = "No Root Word";
        else
            this.verbRootWord = verbRootWord.trim();

        if (this.category.contains("1a"))
            gender = API_KEYS.MALE;
        else if (this.category.contains("1b"))
            gender = API_KEYS.FEMALE;
        else if (this.category.contains("1c"))
            gender = API_KEYS.NEUTRAL;
        else
            gender = API_KEYS.NULL;

        germanTranslationWithoutArticle = germanTranslation.toLowerCase().trim()
                .replace("ü", "u")
                .replace("ö", "o")
                .replace("ä", "a")
                .replace("ß", "s");

        this.germanTranslation = updateGender(gender) + germanTranslation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGermanTranslation(String germanTranslation) {
        this.germanTranslation = germanTranslation;
    }

    public void setEnglishTranslation(String mEnglishTranslation) {
        this.englishTranslation = mEnglishTranslation;
    }

    public void setGermanTranslationWithoutArticle(String mGermanTranslationWithoutArticle) {
        this.germanTranslationWithoutArticle = mGermanTranslationWithoutArticle;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setGermanOpposite(String mGermanOpposite) {
        this.germanOpposite = mGermanOpposite;
    }

    public void setGermanOppositeMeaning(String mGermanOppositeMeaning) {
        this.germanOppositeMeaning = mGermanOppositeMeaning;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String mGender) {
        this.gender = mGender;
    }

    public void setGermanPlural(String mGermanPlural) {
        this.germanPlural = mGermanPlural;
    }

    public void setNumberValue(int mNumber) {
        this.numberValue = mNumber;
    }

    public void setVerbRootWord(String mVerbRootWord) {
        this.verbRootWord = mVerbRootWord;
    }

    public void setVerbPartizip(String mVerbPartizip) {
        this.verbPartizip = mVerbPartizip;
    }

    public void setHelpingVerb(String mHelpingVerb) {
        this.helpingVerb = mHelpingVerb;
    }

    public String getGermanOppositeMeaning() {
        return germanOppositeMeaning;
    }

    public String getGermanTranslation() {
        return /*updateGender(mGender) +*/ germanTranslation;
    }

    public String getGermanTranslationWithoutArticle() {
        return germanTranslationWithoutArticle;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public String getGermanPlural() {
        return germanPlural;
    }

    public String getVerbRootWord() {
        return verbRootWord;
    }

    private String updateGender(String gender) {
        switch (gender) {
            case API_KEYS.NEUTRAL:
                return "das ";
            case API_KEYS.MALE:
                return "der ";
            case API_KEYS.FEMALE:
                return "die ";
            default:
                return "";
        }
    }

    public String getCategory() {
        return category;
    }

    public String getVerbPartizip() {
        return verbPartizip;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public String getGermanOpposite() {
        return germanOpposite;
    }

    public String getHelpingVerb() {
        return helpingVerb;
    }

    public boolean hasPlural() {
        return germanPlural != null;
    }

    public boolean hasOpposite() {
        return germanOpposite != null;
    }

    public boolean hasPartizip() {
        return verbPartizip != null;
    }

    @NonNull
    @Override
    public String toString() {
        return "Word{" +
                "mGermanOpposite='" + germanOpposite + '\'' +
                "mGermanOppositeMeaning='" + germanOppositeMeaning + '\'' +
                ", mGermanTranslation='" + germanTranslation + '\'' +
                ", mEnglishTranslation='" + englishTranslation + '\'' +
                ", mGender=" + gender +
                ", mGermanPlural='" + germanPlural + '\'' +
                ", mCategory='" + category + '\'' +
                ", mNumber=" + numberValue +
                ", mVerbRootWord='" + verbRootWord + '\'' +
                ", mVerbPartizip='" + verbPartizip + '\'' +
                ", mGermanTranslationWithoutArticle='" + germanTranslationWithoutArticle + '\'' +
                ", mHelpingVerb='" + helpingVerb + '\'' +
                '}';
    }
}
