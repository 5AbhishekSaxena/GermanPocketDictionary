package com.abhishek.germanPocketDictionary.utilities;

import android.content.SharedPreferences;

import com.abhishek.germanPocketDictionary.model.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

}
