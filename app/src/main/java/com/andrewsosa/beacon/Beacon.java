package com.andrewsosa.beacon;

import android.app.Application;

import com.firebase.client.Firebase;


public class Beacon extends Application {

    public static final String URL = "https://beacondata.firebaseio.com";
    public static final String PREFS = "beacon";
    public static final String ACTIVE_GROUP_KEY = "activegroupkey";
    public static final String ACTIVE_GROUP_NAME = "activegroupname";

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

    }
}
