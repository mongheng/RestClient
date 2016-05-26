package com.earmongheng.restclient.helper;

import android.os.AsyncTask;

import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.models.User;
import com.earmongheng.restclient.utility.ConvertData;

/**
 * Created by earmongheng on 5/26/2016.
 */
public class SaveTask extends AsyncTask<String, Void, User>{

    private User user;
    private House house;

    public SaveTask(User user, House house) {
        this.user = user;
        this.house = house;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
    }

    @Override
    protected User doInBackground(String... params) {
        if (user != null && house == null) {
            ConvertData.postData(params[0], user, null);
        }else if (user != null && house != null){
            ConvertData.postData(params[0],user, house);
        }
        return user;
    }
}
