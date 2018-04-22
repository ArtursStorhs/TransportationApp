package com.tsi.storhs.transportationapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AsyncConnection extends AsyncTask<Boolean, Boolean, Boolean> {

    private JSONObject loggingPerson;
    public AsyncResponse delegate = null;

    @Override
    protected void onPostExecute(Boolean result) {
        delegate.processFinish(result);
    }

    public void initLoggingPerson(JSONObject loggingPerson) {
        this.loggingPerson = loggingPerson;
        execute(true);
    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(45, TimeUnit.SECONDS).build();

        final HttpUrl build = new HttpUrl.Builder()
                .scheme("http")
                .host("192.168.1.105")
                .port(8889)
                .addPathSegment("mob")
                .build();

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, loggingPerson.toString());
        Request request = new Request.Builder().url(build).post(body).build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject reader = new JSONObject(response.body().string());
            return reader.getBoolean("isExisting");
        } catch (IOException e) {
            Log.e("some", "shit", e);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
