package edu.iastate.graysonc.fastfood.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.iastate.graysonc.fastfood.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private View cancelButton;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        cancelButton = findViewById(R.id.search_cancel);
        cancelButton.setOnClickListener(this);

        searchInput = findViewById(R.id.search_input);
        searchInput.requestFocus();
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
}
