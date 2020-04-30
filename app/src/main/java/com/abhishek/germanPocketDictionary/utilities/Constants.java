package com.abhishek.germanPocketDictionary.utilities;

/**
 * @author Abhishek Saxena
 * @since 18/4/20 10:21 AM
 */

public class Constants {

    public static final String USERNAME_ANONYMOUS = "Anonymous";
    public static final String DEV_MAIL = "5abhisheksaxena@gmail.com";

    public interface API_KEYS {
        String NULL = "gender_null";
        String MALE = "gender_male";
        String FEMALE = "gender_female";
        String NEUTRAL = "gender_neutral";

        String FRAGMENT_TYPE = "fragment_type";

        //String CATEGORY_ALL_WORDS = "category_all_words";
        String CATEGORY_NOUNS = "category_nouns";
        String CATEGORY_VERBS = "category_verbs";
        String CATEGORY_NUMBERS = "category_numbers";
        String CATEGORY_COLORS = "category_colors";
        String CATEGORY_QUESTIONS = "category_questions";
        String CATEGORY_OPPOSITE = "category_opposite";

        String PREF_AGREEMENT_KEY = "pref_agreement_key";

        String USERNAME = "username";
        String FEEDBACK = "feedback";
        String ADDITIONAL_INFORMATION = "additional_information";
    }

    public interface TABLES {
        String ALL_WORDS = "words";
    }
    /*
    public interface FRAGMENT_TYPE{
        public static final int ALL_WORDS = 0;
        public static final int NOUNS = 1;
        public static final int VERBS = 2;
        public static final int NUMBERS = 3;
        public static final int COLORS = 4;
        public static final int QUESTIONS = 5;
        public static final int OPPOSITE = 6;
    }*/

}
