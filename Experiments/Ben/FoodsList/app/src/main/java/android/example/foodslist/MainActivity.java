package android.example.foodslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView FoodTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FoodTextView = (TextView) findViewById(R.id.tv_toy_names);
        String[] Food = FoodList.getFoodPlaces();

        for(String location : Food){
            FoodTextView.append(location + "\n\n\n");
        }



    }

    public void addLocation(){
        //FoodTextView.append(findViewById(R.id.editText3).toString());
        //try{FoodTextView.append("McDanks");}catch(Exception E){E.printStackTrace();}
    }
}
