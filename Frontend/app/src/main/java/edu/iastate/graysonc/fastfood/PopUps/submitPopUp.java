package edu.iastate.graysonc.fastfood.PopUps;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Ticket;

public class submitPopUp extends Activity {
   private Button mSubmit;
   private EditText mQueryInput;
   private Spinner mSpinner;
   private Ticket ticket;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticketpopup);
        mSubmit = findViewById(R.id.SubmitButton);
        mQueryInput = findViewById(R.id.QueryInputBox);
        mSpinner = findViewById(R.id.spinner);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels *.95),(int)(dm.heightPixels *.85));

        mSubmit.setOnClickListener(View -> {
            //TODO I can't figure out how to inject stuff here rip, so I can't get the users Id
            ticket = new Ticket("",mQueryInput.getText().toString().replaceAll("\n"," "),mSpinner.getSelectedItem().toString());
            Toast.makeText(this,"Thank you for your input, we'll be back to you soon!", Toast.LENGTH_LONG).show();
            this.finish();
        });


        List<String> categories = new ArrayList<>();
        categories.add("Contact us");
        categories.add("Error");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
    }
}
