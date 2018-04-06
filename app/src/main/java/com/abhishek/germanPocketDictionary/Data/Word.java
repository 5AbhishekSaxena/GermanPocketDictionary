package com.abhishek.germanPocketDictionary.Data;

import java.io.Serializable;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class Word implements Serializable {

    private String mGermanOpposite;
    private String mGermanOppositeMeaning;
    private String mGermanTranslation;
    private String mEnglishTranslation;
    private int mGender;
    private String mGermanPlural;
    private String mCategory;
    private int mNumber;
    private String mVerbRootWord;
    private String mVerbPartizip;
    private String mGermanTranslationWithoutArticle;
    private String mHelpingVerb;


    final private int GENDER_NULL = 0;
    final private int GENDER_MALE = 1;
    final private int GENDER_FEMALE = 2;
    final private int GENDER_NEUTRAL = 3;


    public Word(String germanTranslation, String englishTranslation, String germanPlural,
                String category, String number, String verbRootWord, String verbPartizip,
                String helpingVerb, String germanOpposite, String germanOppositeMeaning) {

        mEnglishTranslation = englishTranslation;
        if (germanPlural.equals("-1"))
            mGermanPlural = "No Plural";
        else if(!germanPlural.equals("0"))
            mGermanPlural = germanPlural.trim();
        else
            mGermanPlural = null;


        if (!verbPartizip.equals("0"))
            mVerbPartizip = verbPartizip.trim();
        else
            mVerbPartizip = null;

        if (!helpingVerb.equals("0"))
            mHelpingVerb = helpingVerb.trim();
        else
            mHelpingVerb = null;


        mCategory = category;
        mNumber = Integer.valueOf(number);

        if (germanOpposite.equals("0")) {
            mGermanOpposite = null;
            mGermanOppositeMeaning = null;
        }else{
            mGermanOpposite = germanOpposite.trim();
            mGermanOppositeMeaning = germanOppositeMeaning.trim();
        }

        if (verbRootWord.equals("-1"))
            mVerbRootWord = "No Root Word";
        else
            mVerbRootWord = verbRootWord.trim();

        if (mCategory.contains("1a"))
            mGender = GENDER_MALE;
        else if (mCategory.contains("1b"))
            mGender = GENDER_FEMALE;
        else if (mCategory.contains("1c"))
            mGender = GENDER_NEUTRAL;
        else
            mGender = GENDER_NULL;

        mGermanTranslationWithoutArticle = germanTranslation.toLowerCase().trim()
                .replace("ü", "u")
                .replace("ö", "o")
                .replace("ä", "a")
                .replace("ß", "s");

        mGermanTranslation = updateGender(mGender) + germanTranslation;

    }

    public String getmGermanOppositeMeaning() {
        return mGermanOppositeMeaning;
    }

    public String getmGermanTranslation() {
        return /*updateGender(mGender) +*/ mGermanTranslation;
    }

    public String getmGermanTranslationWithoutArticle() {
        return mGermanTranslationWithoutArticle;
    }

    public String getmEnglishTranslation() {
        return mEnglishTranslation;
    }

    public String getmGermanPlural() {
        return mGermanPlural;
    }

    public String getmVerbRootWord() {
        return mVerbRootWord;
    }

    private String updateGender(int gender) {
        switch (gender) {
            case GENDER_NEUTRAL:
                return "das ";
            case GENDER_MALE:
                return "der ";
            case GENDER_FEMALE:
                return "die ";
            default:
                return "";
        }
    }

    public String getmCategory() {
        return mCategory;
    }

    public String getmVerbPartizip() {
        return mVerbPartizip;
    }

    public int getmNumber() {
        return mNumber;
    }

    public String getmGermanOpposite() {
        return mGermanOpposite;
    }

    public String getmHelpingVerb() {
        return mHelpingVerb;
    }

    public boolean hasPlural() {
        return mGermanPlural != null;
    }

    public boolean hasOpposite() {
        return mGermanOpposite != null;
    }

    public boolean hasPartizip() {
        return mVerbPartizip != null;
    }


    @Override
    public String toString() {
        return "Word{" +
                "mGermanOpposite='" + mGermanOpposite + '\'' +
                "mGermanOppositeMeaning='" + mGermanOppositeMeaning + '\'' +
                ", mGermanTranslation='" + mGermanTranslation + '\'' +
                ", mEnglishTranslation='" + mEnglishTranslation + '\'' +
                ", mGender=" + mGender +
                ", mGermanPlural='" + mGermanPlural + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mNumber=" + mNumber +
                ", mVerbRootWord='" + mVerbRootWord + '\'' +
                ", mVerbPartizip='" + mVerbPartizip + '\'' +
                ", mGermanTranslationWithoutArticle='" + mGermanTranslationWithoutArticle + '\'' +
                ", mHelpingVerb='" + mHelpingVerb + '\'' +
                '}';
    }
}
