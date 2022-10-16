package com.abhishek.germanPocketDictionary.utilities;

import android.content.Context;
import android.util.Log;

import com.abhishek.germanPocketDictionary.model.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Abhishek Saxena
 * @since 21/4/20 1:10 PM
 */

public class Utils {

    public static <T> String getJsonFromList(List<T> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<Word> getWordListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Word>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static List<Word> getWordsUsingCategory(Context context, String category) {
        List<Word> allWords = SharedPreferenceManager.getInstance(context).getListFromPreference(Constants.TABLES.ALL_WORDS);
        if (allWords == null) allWords = new ArrayList<>();
        List<Word> filteredList = new ArrayList<>();
        if (category.equals(Constants.TABLES.ALL_WORDS)) {
            filteredList.addAll(allWords);
        } else if (!category.equals(Constants.API_KEYS.CATEGORY_OPPOSITE)) {
            if (allWords != null && !allWords.isEmpty()) {
                for (Word word : allWords) {
                    if (word.getCategory().equals(category))
                        filteredList.add(word);
                }
            }
        } else {
            if (allWords != null && !allWords.isEmpty()) {
                for (Word word : allWords) {
                    if (word.hasOpposite()) {
                        if (filteredList.isEmpty())
                            filteredList.add(word);
                        else {
                            if (!Pattern.compile(Pattern.quote(word.getEnglishTranslation()), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(filteredList.toString()).find())
                                filteredList.add(word);
                        }
                    }
                }
            }
        }
        sortWords(filteredList, category);
        return filteredList;
    }

    private static void sortWords(List<Word> words, String category) {
        if (category.equals(Constants.API_KEYS.CATEGORY_NUMBERS)){
            //Sort numberList
            Collections.sort(words, (s1, s2) ->
                    Integer.compare(s1.getNumberValue(), s2.getNumberValue()));
        }else{
            //sort the words
            Collections.sort(words, (s1, s2) ->
                    s1.getGermanTranslationWithoutArticle().compareTo(s2.getGermanTranslationWithoutArticle()));
        }
        Log.v("Utils", words.get(0).toString());
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
