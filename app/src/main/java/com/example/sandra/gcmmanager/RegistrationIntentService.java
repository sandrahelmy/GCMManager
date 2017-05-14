package com.example.sandra.gcmmanager;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by Sandra on 5/13/2017.
 */
public class RegistrationIntentService extends IntentService {
    public RegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("myTag", "token = " + token);

            GcmPubSub pubSub = GcmPubSub.getInstance(this);

            pubSub.subscribe(token, "/topics/manager", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
