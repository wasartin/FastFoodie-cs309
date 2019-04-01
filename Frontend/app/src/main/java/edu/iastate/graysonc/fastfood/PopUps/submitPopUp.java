package edu.iastate.graysonc.fastfood.PopUps;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Ticket;

public class submitPopUp extends Activity implements View.OnClickListener {
   private Button mSubmit;
   private EditText mQueryInput;
   private Spinner mSpinner;
   private Ticket ticket;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSubmit = findViewById(R.id.SubmitButton);
        mSubmit.setOnClickListener(this);
        mQueryInput = findViewById(R.id.QueryInputBox);
        mSpinner = findViewById(R.id.spinner);
        setContentView(R.layout.ticketpopup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        getWindow().setLayout((int) (dm.widthPixels *.95),(int)(dm.heightPixels *.85));
    }


    @Override
    public void onClick(View v) {

    }
}
