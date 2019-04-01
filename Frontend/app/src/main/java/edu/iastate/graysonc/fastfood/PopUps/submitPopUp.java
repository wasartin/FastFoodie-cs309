package edu.iastate.graysonc.fastfood.PopUps;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;

import edu.iastate.graysonc.fastfood.R;

public class submitPopUp extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ticketpopup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (dm.widthPixels *.8),(int)(dm.heightPixels *.6));
    }
}
