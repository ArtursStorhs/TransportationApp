package com.tsi.storhs.transportationapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements AsyncResponse {
    AsyncConnection asyncConnection = new AsyncConnection();
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        asyncConnection.delegate = this;

        Button button = findViewById(R.id.LoggingButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                JSONObject loggingPerson = new JSONObject();
                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);

                try {
                    loggingPerson.put("username", username.getText());
                    loggingPerson.put("password", password.getText());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                asyncConnection.initLoggingPerson(loggingPerson);
            }
        });
    }

    @Override
    public void processFinish(Boolean output) {
        if (output) {

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set title
            alertDialogBuilder.setTitle("This User doesnt exist");

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }
}
