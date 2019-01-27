package edu.iastate.graysonc.moderndashboardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.RecognitionListener;

public class SpeechToTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);
    }
}
