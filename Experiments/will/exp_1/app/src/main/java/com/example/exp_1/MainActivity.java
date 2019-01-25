package com.example.exp_1;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";//Helps with searching for where the error might have occured

    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Something");//For Debugging, logs into LogCat
        //You can right click onto a breakpoint and introduce a condition.
        //So when the condition is met, it will break there.

        for (int i = 0; i < 5; i++) {//You can add variables to 'watch' to monitor them during a debug session
            Log.i(TAG, "Current i = " + i);
        }
        b1 = (Button) findViewById(R.id.buttonMessage);
        b2 = (Button) findViewById(R.id.buttonNext);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "Hello Android !", Toast.LENGTH_LONG).show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

    }
}
