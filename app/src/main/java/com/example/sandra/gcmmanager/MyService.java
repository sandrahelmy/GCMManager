package com.example.sandra.gcmmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;


public class MyService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle bundle) {
        super.onMessageReceived(from, bundle);

//        MainActivity.sendNotification("Task Taken" , "Sorry, Someone else has taken the job !");
        Intent jobAccepted = new Intent(this, JobAccepted.class);
        startActivity(jobAccepted);






    }
}
