package android.example.apiquerry;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    //Build the request
    //https://api.nal.usda.gov/ndb/reports/?ndbno=01009&type=b&format=json&api_key=DEMO_KEY
    final static String Food_Base_URL = "https://api.nal.usda.gov/ndb/reports/?ndbno=";
    final static String Food_End_URL = "&type=b&format=json&api_key=I2C6LnMa1DmUNp1DnjtiGzDQUSAlJcEA1st7tRtt";
    final static String Example = "21334";
    //Everything we need from the XML File
    EditText SearchBoxText;
    TextView ResultsText;
    TextView ErrorMessage;
    ProgressBar LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set everything to its corresponding location
        SearchBoxText = (EditText) findViewById(R.id.querry_search_box);
        ResultsText = (TextView) findViewById(R.id.display_results);
        ErrorMessage = (TextView) findViewById(R.id.error_message);
        LoadingBar = (ProgressBar) findViewById(R.id.progress_circular);
    }

    private void makeQuerry(){
        String foodQuerry = SearchBoxText.getText().toString();
        URL foodSearchURL = buildURL(foodQuerry);
        new QuerryTask().execute(foodSearchURL);
    }

    private void noError(){
        ErrorMessage.setVisibility(View.INVISIBLE);
        ResultsText.setVisibility(View.VISIBLE);
    }

    private void wasError(){
        ErrorMessage.setVisibility(View.VISIBLE);
        ResultsText.setVisibility(View.INVISIBLE);
    }


    //Background Network Task
    public class QuerryTask extends AsyncTask<URL, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String results = null;
            try{
                results = getAPIData(searchURL);
            }catch(Exception e){e.printStackTrace();}
            return results;

        }

        @Override
        protected void onPostExecute(String s) {
            LoadingBar.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                noError();
                ResultsText.setText(s);
            } else {
                wasError();
            }
        }
    }

    //Menu Functions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int Clicked = item.getItemId();
        if(Clicked == R.id.action_search){
            makeQuerry();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Handlers for URLS
    public static URL buildURL(String query){
        try {
            return new URL(Food_Base_URL + query + Food_End_URL);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getAPIData(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
