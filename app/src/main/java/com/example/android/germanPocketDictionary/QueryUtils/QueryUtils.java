package com.example.android.germanPocketDictionary.QueryUtils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.germanPocketDictionary.Data.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek Saxena on 1/20/2018.
 */

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static String mJsonresponse;

    QueryUtils(String jsonResponse) {
        mJsonresponse = jsonResponse;
    }

    public static List<Word> extractFeatureFromJson(String germanJson) {

        if (TextUtils.isEmpty(germanJson)) {
            return null;
        }

        List<Word> words = new ArrayList<>();
        try{
            JSONObject baseJsonResponse = new JSONObject(germanJson);
             JSONObject germanFeed = baseJsonResponse.getJSONObject("feed");
            JSONArray germanArray = germanFeed.getJSONArray("entry");

            for (int i = 0; i < germanArray.length() ; i++){
                JSONObject currentGerman = germanArray.getJSONObject(i);

                JSONObject contentGerman = currentGerman.getJSONObject("content");
                 String contentGermanData = contentGerman.getString("$t");

                JSONObject germanTranslation = currentGerman.getJSONObject("gsx$germantranslation");
                String germanTranslationValue = String.valueOf(germanTranslation.getString("$t"));

                JSONObject englishTranslation = currentGerman.getJSONObject("gsx$englishtranslation");
                String englishTranslationValue = String.valueOf(englishTranslation.getString("$t"));

                JSONObject germanPlural = currentGerman.getJSONObject("gsx$plural");
                String germanPluralValue = germanPlural.getString("$t");

                JSONObject number = currentGerman.getJSONObject("gsx$numbervalue");
                String numberValue = number.getString("$t");

                JSONObject category = currentGerman.getJSONObject("gsx$category");
                String categoryValue = category.getString("$t");

                JSONObject verbRootWord = currentGerman.getJSONObject("gsx$verbrootword");
                String verbRootWordValue = verbRootWord.getString("$t");

                Word word = new Word(germanTranslationValue, englishTranslationValue, germanPluralValue,categoryValue, numberValue, verbRootWordValue);

                words.add(word);
            }

        }catch (JSONException e){
            Log.e(LOG_TAG, "Problem parsing the germanPocketDictionary sheets JSON results", e);
        }

        return words;
    }

    public static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse ="";

        if(url== null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream =urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the German sheets JSON results.", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }

        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Word> fetchGermanData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Word> words = extractFeatureFromJson(jsonResponse);

        return words;
    }

    public String getmJsonresponse(){
        return mJsonresponse;
    }

}
