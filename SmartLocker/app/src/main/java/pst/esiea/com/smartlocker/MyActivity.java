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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyActivity extends ActionBarActivity {
    private Button mButton;

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


                JSonTask jsonTask = new JSonTask();
                jsonTask.execute();



            }
        });
    }

    private class JSonTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... args) {
            //Log.d(TAG, "Je rentre en JSON");

            try {
                return getStatut();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "error dans lecture JSON";
        }


        private String getStatut() throws JSONException {
            //Log.d(TAG,"Je rentre dans getStatut");
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.getJSONFromUrl(url);
            //Log.d(TAG, "json OK");
            JSONArray with = json.getJSONArray("with");
            //Log.d(TAG, "with OK");
            JSONObject withN0 = with.getJSONObject(0);
            //Log.d(TAG, "withN0 OK");
            JSONObject content = withN0.getJSONObject("content");
            //Log.d(TAG, "content OK : "+content.toString());
            String aStatut = content.getString("open_closed");
            //Log.d(TAG, "aStatut OK");



            //Log.d(TAG, "J'ai parsé mon JSON");


            //Log.d(TAG, "le statut est :" + aStatut);

            switch (aStatut){
                case "0":
                    aStatut = "Porte fermée !! ";
                    break;
                default:
                    aStatut = "Porte ouverte !! ";
                    break;

            }


            //Log.d(TAG, "le statut est :" + aStatut);

            return aStatut;

        }

        protected void onPostExecute(String aStatut){

            Context context = getApplicationContext();

            CharSequence text = aStatut;


            //text = aStatut;
            // Log.d("tag name",aStatut);
            //Log.d(TAG, "text=" + text);
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();



        }
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


    //Ph7YXtr
    public void sendOpen(View view){
        Opener opener = new Opener();
        opener.execute();
    }

    public void sendClose(View view){
        Closer closer = new Closer();
        closer.execute();
    }

    private class Opener extends AsyncTask<Boolean, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(Boolean... params) {

            HttpPoster poster = new HttpPoster("http://81.65.225.160/deverrouillage.php");
            EditText editText = (EditText) findViewById(R.id.edit_password);
            String message = editText.getText().toString();

            return poster.posting(message, true);
        }

        protected void onPostExecute(Boolean state){

            if(state = true) {

                JSonTask jsontask = new JSonTask();
                jsontask.execute();

            }else{
                Context context = getApplicationContext();

                CharSequence text = "Denied";

                //Log.d(TAG, "text=" + text);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }


        }
    }

    private class Closer extends AsyncTask<Boolean, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(Boolean... params) {

            HttpPoster poster = new HttpPoster("http://81.65.225.160/deverrouillage.php");
            EditText editText = (EditText) findViewById(R.id.edit_password);
            String message = editText.getText().toString();

            return poster.posting(message,false);
        }

        protected void onPostExecute(Boolean state){

            if(state = true) {

                JSonTask jsontask = new JSonTask();
                jsontask.execute();

            }else{
                Context context = getApplicationContext();

                CharSequence text = "Denied";

                //Log.d(TAG, "text=" + text);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }


        }
    }
}
