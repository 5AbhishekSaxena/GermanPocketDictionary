package com.abhishek.germanPocketDictionary.utilities;

import android.content.Context;

import com.abhishek.germanPocketDictionary.model.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abhishek Saxena
 * @since 21/4/20 1:10 PM
 */

public class Utils {

    public static  <T> String getJsonFromList(List<T> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<Word> getWordListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Word>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static List<Word> getWordsUsingCategory(Context context, String category){
        List<Word> allWords = SharedPreferenceManager.getInstance(context).getListFromPreference(Constants.TABLES.ALL_WORDS);
        if (category.equals(Constants.TABLES.ALL_WORDS))
            return allWords;

        List<Word> filteredList = new ArrayList<>();
        if (allWords != null && !allWords.isEmpty()) {
            for (Word word : allWords){
                if (word.getCategory().equals(category))
                    filteredList.add(word);
            }
        }
        return filteredList;
    }

    public static int getFragmentLocationFromCategory(String category) {
        int location = 1;

        switch (category) {
            case Constants.TABLES.ALL_WORDS:
                location = 0;
                break;
            case Constants.API_KEYS.CATEGORY_NOUNS:
                location = 1;
                break;
            case Constants.API_KEYS.CATEGORY_VERBS:
                location = 2;
                break;
            case Constants.API_KEYS.CATEGORY_NUMBERS:
                location = 3;
                break;
            case Constants.API_KEYS.CATEGORY_COLORS:
                location = 4;
                break;
            case Constants.API_KEYS.CATEGORY_QUESTIONS:
                location = 5;
                break;
            case Constants.API_KEYS.CATEGORY_OPPOSITE:
                location = 6;
                break;
        }
        return location;
    }

}
