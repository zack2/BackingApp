package com.nanodegree.udacy.backingapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
            startActivity(new Intent(this,MainActivity.class));
            if( Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                SplashActivity.this.finishAffinity();
            }else {
                 SplashActivity.this.finish();
            }


    }


}
