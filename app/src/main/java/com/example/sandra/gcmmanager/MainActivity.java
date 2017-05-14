package com.example.sandra.gcmmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    Button sendNotificationButton;
    Button taskTakenButton;
    static String requestURL = "https://fcm.googleapis.com/fcm/send";
    static String key = "AIzaSyB_EZJ0nUfa0bDpkVI722OeQVRx0_uV7Jo";
    static String toToken = "cZ6S73YJlBU:APA91bHp0yhNiM0rSNvdn7gUxM66LeVBSPChHpleFFfNyBUGZcS-o8ionYgyzDhNrUzyTLZ0mpcRlQAKVbSY_FhtvrwxxMO4_6ie1EynCYzgSGkI28U81kolJ1oCeT4BV3TphMY-tMa_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendNotificationButton = (Button) findViewById(R.id.sendNotification);
        taskTakenButton = (Button) findViewById(R.id.taskTakenButton);
        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyAsync().execute("Task Required", "This task is required immediately");

            }
        });
        taskTakenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyAsync().execute("Task Taken", "Sorry, Someone else has taken the job !");

            }
        });

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

    }

    public static void sendNotification(String notificationTitle, String notificationBody) {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(requestURL);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "key="
                    + key);

            JSONObject root = new JSONObject();
            JSONObject notification = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("title", notificationTitle);
            notification.put("title", notificationTitle);
            notification.put("body", notificationBody);
            if (notificationTitle.equals("Task Required")) {
                notification.put("click_action", "OPEN_TASK_REQUIRED");
            } else {
                notification.put("click_action", "OPEN_TASK_TAKEN");
            }

            root.put("notification", notification);
            root.put("data", data);
//            root.put("to", toToken);
//            root.put("to", "/topics/global");
            root.put("condition", "'global' in topics");

            byte[] outputBytes = root.toString().getBytes("UTF-8");
            OutputStream os = connection.getOutputStream();
            os.write(outputBytes);
            os.flush();
            os.close();
            connection.getInputStream();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /***
     * Async class
     ***/
    //params, progress, result
    public class MyAsync extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            sendNotification(strings[0], strings[1]);
            return null;
        }

    }


}
