package edu.iastate.graysonc.moderndashboardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GreeterActivity extends AppCompatActivity {
    TextView textView;
    Button greetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeter);

        textView = (TextView) findViewById(R.id.textView);
        greetButton = (Button) findViewById(R.id.greetButton);
        greetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Hello");
            }
        });
    }
}
