package com.abhishek.germanPocketDictionary.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.abhishek.germanPocketDictionary.model.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static com.abhishek.germanPocketDictionary.utilities.Utils.getJsonFromList;

/**
 * @author Abhishek Saxena
 * @since 19/4/20 11:19 AM
 */

public class SharedPreferenceManager {

    private static SharedPreferenceManager sInstance;
    //private Context context;
    private static SharedPreferences sharedPreferences;

    public static SharedPreferenceManager getInstance(Context context) {
        if (sInstance == null)
            sInstance = new SharedPreferenceManager(context);

        return sInstance;
    }

    private SharedPreferenceManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public <T> void setList(String key, List<T> list) {
        String json = getJsonFromList(list);
        if (json != null && !json.equals("")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, json);
            editor.apply();
        }
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public List<Word> getListFromPreference(String key) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        Type type = new TypeToken<List<Word>>() {
        }.getType();
        return gson.fromJson(json, type);

    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
