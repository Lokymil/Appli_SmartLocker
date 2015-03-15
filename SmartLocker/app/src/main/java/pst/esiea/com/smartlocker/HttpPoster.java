package pst.esiea.com.smartlocker;

import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HttpPoster {

    String url = null;

    public HttpPoster(String url)
    {
        this.url = url;
    }

    public boolean posting(String message, Boolean opening) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("Authentication", message));

            if(opening) {
                nameValuePairs.add(new BasicNameValuePair("Ouvrir", "Ouvrir"));
            }else{
                nameValuePairs.add(new BasicNameValuePair("Fermer", "Fermer"));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            if(response.toString() == "200"){
                return true;
            }else{
                return false;
            }

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        return false;
    }
}
