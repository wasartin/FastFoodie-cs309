package edu.iastate.graysonc.moderndashboardapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void startListEditor(View v) {
        Intent listEditorIntent = new Intent(getApplicationContext(), ListEditorActivity.class);
        startActivity(listEditorIntent);
    }

    protected void startGreeter(View v) {
        Intent greeterIntent = new Intent(getApplicationContext(), GreeterActivity.class);
        startActivity(greeterIntent);
    }

    protected void startMaps(View v) {
        Intent mapsIntent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(mapsIntent);
    }

    protected void startSpeechToText(View v) {
        Intent speechToTextIntent = new Intent(getApplicationContext(), SpeechToTextActivity.class);
        startActivity(speechToTextIntent);
    }
}