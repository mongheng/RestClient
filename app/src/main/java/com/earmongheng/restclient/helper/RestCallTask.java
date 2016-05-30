package com.earmongheng.restclient.helper;

import android.os.AsyncTask;

import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.models.User;
import com.earmongheng.restclient.utility.ConvertData;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earmongheng on 5/8/2016.
 */
public class RestCallTask extends AsyncTask<String, Void, User> {

    @Override
    protected User doInBackground(String... url) {
        try {
            User user = new User("song","fhe12345","mongheng.song@gmail.com","016556955");

            House house = new House(0,100000,45000,"Test",11.23145,11.23453,null, null);

            ConvertData.postData(url[0],user,house);
            return user;
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
    }
}
