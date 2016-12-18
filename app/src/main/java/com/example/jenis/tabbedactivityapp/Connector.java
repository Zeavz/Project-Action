package com.example.jenis.tabbedactivityapp;

import com.firebase.client.Firebase;

/**
 * Created by Jenis on 7/7/2016.
 */
public class Connector extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}