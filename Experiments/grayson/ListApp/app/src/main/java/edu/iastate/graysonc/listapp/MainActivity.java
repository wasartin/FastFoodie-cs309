package edu.iastate.graysonc.listapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView myListView;
    String[] people, towns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        myListView = (ListView)findViewById(R.id.myListView);
        people = res.getStringArray(R.array.people);
        towns = res.getStringArray(R.array.towns);

        ItemAdapter itemAdapter = new ItemAdapter(this, people, towns);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetailActivity = new Intent(getApplicationContext(), DetailActivity.class);
                showDetailActivity.putExtra("edu.iastate.graysonc.ITEM_INDEX", position);
                startActivity(showDetailActivity);
            }
        });
    }
}
