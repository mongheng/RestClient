package com.earmongheng.restclient.helper;

import android.os.AsyncTask;

import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.models.User;

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

            House house = new House(0,100000,45000,"Test",11.23145,11.23453,null);

            postData(url[0],user,house);
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

    private void postData(String url, User user, House house) {

        try {

            List<BasicNameValuePair> users = new ArrayList<BasicNameValuePair>();

            users.add(new BasicNameValuePair("userid",String.valueOf(user.getUserid())));
            users.add(new BasicNameValuePair("username",user.getUsername()));
            users.add(new BasicNameValuePair("password",user.getPassword()));
            users.add(new BasicNameValuePair("telephone",user.getTelephone()));
            users.add(new BasicNameValuePair("email",user.getEmail()));

            users.add(new BasicNameValuePair("houseid",String.valueOf(house.getHouseid())));
            users.add(new BasicNameValuePair("price",String.valueOf(house.getPrice())));
            users.add(new BasicNameValuePair("deposite",String.valueOf(house.getDeposite())));
            users.add(new BasicNameValuePair("description",String.valueOf(house.getDescription())));
            users.add(new BasicNameValuePair("latitute",String.valueOf(house.getLatitute())));
            users.add(new BasicNameValuePair("longtitute",String.valueOf(house.getLongtitute())));
            users.add(new BasicNameValuePair("picture",String.valueOf(house.getPicture())));

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(users);
            entity.setContentEncoding(HTTP.UTF_8);
            httpPost.setEntity(entity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
        }
        catch(Exception ex){

        }
    }
}
