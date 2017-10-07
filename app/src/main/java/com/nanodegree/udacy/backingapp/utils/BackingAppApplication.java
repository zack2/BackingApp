package com.nanodegree.udacy.backingapp.utils;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

//import info.androidhive.volleyexamples.volley.utils.LruBitmapCache;

public class BackingAppApplication extends Application {
    private static BackingAppApplication mInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    public void onCreate() {
        super.onCreate();
       // Stetho.initializeWithDefaults(this);
        Fabric.with(this, new Crashlytics());
    }

    public static BackingAppApplication get() {
        return mInstance;
    }

    public BackingAppApplication() {
    }

    private BackingAppApplication(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

       // Stetho.initializeWithDefaults(context);
    }

    public static synchronized BackingAppApplication getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BackingAppApplication(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}