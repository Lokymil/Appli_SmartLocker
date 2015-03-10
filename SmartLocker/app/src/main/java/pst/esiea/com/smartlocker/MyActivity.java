package pst.esiea.com.smartlocker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyActivity extends ActionBarActivity {
    private Button mButton;
    String aStatut;
    private static final String url = "https://dweet.io/get/latest/dweet/for/PSTArduino";
    private static final String arduino_state = "running";
    private static final String TAG = "MyActivity";

    public final static String EXTRA_MESSAGE = "pst.esiea.com.smartLocker.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mButton = (Button) findViewById(R.id.status);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSonTask json = new JSonTask();

                Context context = getApplicationContext();

                CharSequence text = null;

                text=json.getStatus().toString();

                //text = aStatut;
                // Log.d("tag name",aStatut);
                Log.d(TAG, "text=" + text);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


            }
        });
    }

    private class JSonTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... args) {
            Log.d(TAG, "Je rentre en JSON");

            return String.valueOf(getStatus());

        }
    }


    private String getStatus() throws JSONException {
        JSONParser jParser = new JSONParser();
        JSONArray json = jParser.getJSONFromUrl(url);
        String aStatut = null;

        Log.d(TAG, "J'ai parsé mon JSON");

        for (int i = 0; i < json.length(); i++) {

            JSONObject c = json.getJSONObject(i);
            aStatut = c.getString(arduino_state);

        }
        Log.d(TAG,"le statut est :" + aStatut);

        return aStatut;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case R.id.action_about:
                openAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAbout() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }


    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
