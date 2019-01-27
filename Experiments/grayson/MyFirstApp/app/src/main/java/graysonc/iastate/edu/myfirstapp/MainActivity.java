package graysonc.iastate.edu.myfirstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView greetingTextView = (TextView) findViewById(R.id.greetingTextView);
        Button greetButton = (Button) findViewById(R.id.greetingButton);
        greetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greetingTextView.setText("Hello");
            }
        });

    }
}
