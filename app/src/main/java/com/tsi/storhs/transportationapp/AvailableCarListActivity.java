package com.tsi.storhs.transportationapp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class AvailableCarListActivity extends Activity implements AsyncCarListResponse {

    SessionStore sessionStore;
    BitmapFactory.Options options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionStore = new SessionStore(this);
        AsyncConnectionGetJson asyncConnectionGetJson = new AsyncConnectionGetJson();
        asyncConnectionGetJson.delegate = AvailableCarListActivity.this;
        JSONObject loggingPerson = new JSONObject();

        String username = sessionStore.getusename();

        try {
            loggingPerson.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        asyncConnectionGetJson.initLoggingPerson(loggingPerson);
    }

    @Override
    public void processFinishJson(JSONObject output) {

        if (output != null && output.length() > 0) {

            setContentView(R.layout.activity_available_car_list);
            TextView tv = (TextView) findViewById(R.id.PlaceWherePickACarData);
            tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            ImageView imageView = (ImageView) findViewById(R.id.carPictureView);
            TextView manufTextView = (TextView) findViewById(R.id.carManufacturerData);
            TextView modelTextView = (TextView) findViewById(R.id.carModelData);
            TextView registrationNumberTextView = (TextView) findViewById(R.id.carRegistartionNumberData);
            TextView yearTextView = (TextView) findViewById(R.id.carYearData);
            TextView priceTextView = (TextView) findViewById(R.id.carPriceData);
            TextView dateFromTextView = (TextView) findViewById(R.id.DateFromData);
            TextView dateToTextView = (TextView) findViewById(R.id.DateToData);

            try {

                String[] splitPath = output.get("Photo").toString().split("/");
                String fileName = splitPath[2].substring(0, splitPath[2].indexOf("."));
                int imageId = this.getResources().getIdentifier(fileName, "drawable", getPackageName());
                imageView.setImageResource(imageId);

                manufTextView.setText(output.get("Manufacturer").toString());
                modelTextView.setText(output.get("Model").toString());
                registrationNumberTextView.setText(output.get("RegistrationNumber").toString());
                yearTextView.setText(output.get("Year").toString());
                priceTextView.setText(output.get("Price").toString());
                dateFromTextView.setText(output.get("DateFrom").toString());
                dateToTextView.setText(output.get("DateTo").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            setContentView(R.layout.activity_no_rented_car);
        }

    }

    public void onClick(View view) {
        Uri gmmIntentUri = Uri.parse("geo:56.977657,24.167421?q=" + Uri.encode("Gustava Zemgala Gatve 80"));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}

