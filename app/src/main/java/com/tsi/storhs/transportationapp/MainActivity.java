package com.tsi.storhs.transportationapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new MyAsyncTask().execute(true);
    }

    private class MyAsyncTask extends AsyncTask<Boolean, Boolean, Boolean> {

        @Override protected Boolean doInBackground(Boolean... booleans) {
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(45, TimeUnit.SECONDS).build();

            HttpUrl build = new HttpUrl.Builder()
                    .scheme("http")
                    .host("192.168.1.102")
                    .port(8889)
                    .addPathSegment("mob")
                    .build();
            Request request = new Request.Builder().url(build).header("Some", "Shit").build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                Log.e("some", "shit", e);
            }
            return null;
        }
    }
}