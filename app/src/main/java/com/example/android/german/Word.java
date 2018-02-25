package com.example.android.german;

import java.io.Serializable;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class Word implements Serializable {

    private String mGermanTranslation;
    private String mEnglishTranslation;
    private int mGender;
    private String mGermanPlural;
    private String mCategory;


    final private int GENDER_NULL = 0;
    final private int GENDER_MALE = 1;
    final private int GENDER_FEMALE = 2;
    final private int GENDER_NEUTRAL = 3;

    public Word(String germanTranslation, String englishTranslation, int gender) {
        mGermanTranslation = germanTranslation;
        mEnglishTranslation = englishTranslation;
        mGender = gender;
    }


    public Word(String germanTranslation, String englishTranslation, String germanPlural) {
        mGermanTranslation = germanTranslation;
        mEnglishTranslation = englishTranslation;
        mGermanPlural = germanPlural;
    }


     public Word(String germanTranslation, String englishTranslation) {
        mGermanTranslation = germanTranslation;
        mEnglishTranslation = englishTranslation;
        mGender = GENDER_NULL;
    }

    public Word(String germanTranslation, String englishTranslation, String germanPlural, String category) {
        mGermanTranslation = germanTranslation;
        mEnglishTranslation = englishTranslation;
        mGermanPlural = germanPlural;
        mCategory = category;
        for (int i = 0; i < category.length(); i++) {
            if (mCategory.contains("2"))
                mGender = GENDER_MALE;
            else if (mCategory.contains("3"))
                mGender = GENDER_FEMALE;
            else if (mCategory.contains("4"))
                mGender = GENDER_NEUTRAL;
            else
                mGender = GENDER_NULL;
        }
    }

    public String getmGermanTranslation() {
        return updateGender(mGender) + mGermanTranslation;
    }

    public String getmEnglishTranslation() {
        return mEnglishTranslation;
    }

    public String getmGermanPlural() {
        return mGermanPlural;
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

    @Override
    public String toString() {
        return "Word(" +
                "mEnglishTranslation: " + mEnglishTranslation + '\'' +
                "mGermanTranslation: " + mGermanTranslation + '\'' +
                "mGermanPlural: " + mGermanPlural +
                "}";
    }
}
