package com.earmongheng.restclient.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.models.User;
import com.earmongheng.restclient.utility.ConvertData;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by earmongheng on 5/10/2016.
 */
public class UpdateTask extends AsyncTask<String, Void, User> {

    private HttpClient httpClient;
    private Context context;

    public UpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected User doInBackground(String... url) {

        String result = ConvertData.GET(url[0]);
        User user = ConvertData.extraUserData(result);

        String pathRUL = "http://192.168.1.138:8080/Realestate/update/{userid}";
        updateData(pathRUL,user);
        return user;
    }

    @Override
    protected void onPostExecute(User user) {
        if (user != null) {
            Toast.makeText(context, user.getPassword(), Toast.LENGTH_LONG).show();
            super.onPostExecute(user);
        }
    }

    private void updateData(String url, User user) {

        try {

            user.setPassword("9876543");
            Map<String, String> para = new HashMap<>();
            para.put("userid", String.valueOf(user.getUserid()));
            para.put("username", user.getUsername());
            para.put("password", user.getPassword());
            para.put("telephone", user.getTelephone());
            para.put("email", user.getEmail());

            JSONObject userSon = new JSONObject();
            userSon.put("username", user.getUsername());
            userSon.put("telephone", user.getTelephone());
            userSon.put("password",user.getPassword());
            userSon.put("email",user.getEmail());

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> httpEntity = new HttpEntity<Object>(userSon.toString(),httpHeaders);
            ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Object.class, para);
            //restTemplate.put(url,httpEntity,para);

            Object responseResult = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(responseResult);

            User currentUser = ConvertData.extraUserData(result.toString());
            House currentHouse = ConvertData.extraHouseData(result.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
