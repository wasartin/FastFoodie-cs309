package edu.iastate.graysonc.fastfood.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;

import edu.iastate.graysonc.fastfood.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private View cancelButton;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        cancelButton = findViewById(R.id.search_cancel);
        cancelButton.setOnClickListener(this);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.search_input);
        ComponentName componentName = new ComponentName(this, MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.requestFocus();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancel:
                setResult(RESULT_CANCELED);
                this.finish();
                break;
        }
    }

    private void submitSearch() {
        onSearchRequested();
    }
}
