package com.abhishek.germanPocketDictionary.QueryUtils;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyCalls extends Application {

    private static VolleyCalls mInstance;

    private RequestQueue requestQueue;

    private final String TAG = getClass().getName();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized VolleyCalls getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(this);

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(tag.isEmpty() ? TAG : tag);
        getRequestQueue().add(request);
    }
}
