package com.example.android.german;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by Abhishek Saxena on 1/20/2018.
 */

public class GermanLoader extends AsyncTaskLoader<List<Word>> {

    private static final String LOG_TAG = GermanLoader.class.getName();

    private String mUrl;

    public GermanLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG, "onStartLoading");
        forceLoad();
    }


    @Override
    public List<Word> loadInBackground() {
        if (mUrl == null)
            return null;
        Log.v(LOG_TAG, "loadInBackground");
        List<Word> words = QueryUtils.fetchGermanData(mUrl);
        return words;
    }

}
