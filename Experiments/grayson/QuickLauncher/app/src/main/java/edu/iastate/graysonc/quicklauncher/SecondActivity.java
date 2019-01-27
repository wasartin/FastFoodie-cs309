package edu.iastate.graysonc.quicklauncher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (getIntent().hasExtra("edu.iastate.graysonc.quicklauncher.MESSAGE")) {
            TextView textView = (TextView)findViewById(R.id.textView);
            String message = getIntent().getExtras().getString("edu.iastate.graysonc.quicklauncher.MESSAGE");
            textView.setText(message);
        }
    }
}
