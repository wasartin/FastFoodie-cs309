package edu.iastate.graysonc.fastfood.activities;

import android.app.ListActivity;
import android.os.Bundle;

import edu.iastate.graysonc.fastfood.R;

public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
    }
}
