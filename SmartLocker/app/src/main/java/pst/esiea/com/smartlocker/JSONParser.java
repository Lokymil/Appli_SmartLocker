package pst.esiea.com.smartlocker;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;import java.lang.String;import java.lang.StringBuilder;

public class JSONParser {
    private static final String TAG = "MyActivity";

    static JSONObject jobject = null;

    public JSONObject getJSONFromUrl(String url){

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try{
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if(statusCode == 200) {
                //Log.d(TAG, "StatusCode == 200");
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                //Log.d(TAG,"Buffer seems to be OK");

                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line+"\n");
                }

                //Log.d(TAG, builder.toString());


            }else{

                }
            }

         catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            jobject = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jobject;
    }

}
