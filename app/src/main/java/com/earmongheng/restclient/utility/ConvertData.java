package com.earmongheng.restclient.utility;

import android.util.Log;

import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.models.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by earmongheng on 5/18/2016.
 */
public class ConvertData {

    public static JSONObject extraData(String result) {
        JSONObject jsonObject = null;
        try {
            Object data = new JSONTokener(result).nextValue();
            if (data instanceof JSONObject) {
                jsonObject = (JSONObject) data;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonObject;
    }

    public static User extraUserData(String result) {

        try {
            JSONObject jsonObject = extraData(result);
            User user = new User(jsonObject.getString("username"),jsonObject.getString("password"),
                    jsonObject.getString("email"),jsonObject.getString("telephone"));
            user.setUserid(jsonObject.getInt("userid"));
            if (user != null) {
                return user;
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static List<House> extraHousesData(String result) {

        try {
            List<House> houses = new ArrayList<>();
            Object datas = new JSONTokener(result).nextValue();
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            if (datas instanceof JSONObject) {
                jsonObject = (JSONObject) datas;
            }
            else if (datas instanceof JSONArray){
                jsonArray = (JSONArray) datas;
            }

            for (int i=0; i<jsonArray.length(); i++) {
                JSONArray jsonhouses = jsonArray.getJSONObject(i).getJSONArray("houses");

                if (houses != null) {
                    for (int j = 0; j < jsonhouses.length(); j++) {
                        JSONObject jsonhouse = jsonhouses.getJSONObject(j);

                        House house = new House(jsonhouse.getInt("houseid"),jsonhouse.getLong("price"),jsonhouse.getLong("deposite"),
                                jsonhouse.getString("description"),jsonhouse.getDouble("latitute"),jsonhouse.getDouble("longtitute"),
                                jsonhouse.getString("picture"));

                        houses.add(house);
                    }
                }
            }
            return houses;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static House extraHouseData(String result) {

        House house = null;

        try {
            Object data = new JSONTokener(result).nextValue();

            if (data instanceof JSONObject) {

                JSONObject jsonData = (JSONObject) data;
                JSONObject jsonHouse = jsonData.getJSONArray("houses").getJSONObject(0);

                house = new House(jsonHouse.getInt("houseid"),jsonHouse.getLong("price"),jsonHouse.getLong("deposite"),
                        jsonHouse.getString("description"),jsonHouse.getDouble("latitute"),jsonHouse.getDouble("longtitute"),
                        jsonHouse.getString("picture"));

            }else {
                house = null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return house;
    }

    public static String GET(String url) {

        InputStream inputStream = null;
        String result = "";
        //HttpURLConnection connection;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();

            /*URL url = new URL(urlpara);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            inputStream = connection.getInputStream();*/

            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else {
                result = "Did not work!";
            }
        }
        catch (Exception ex) {
            Log.d("InputStream", ex.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        return result;
    }

    public static <T2,T1> T2 getHttpResponseObject(String url, T1 entityT1, Class<T2> entityT2, Map<String,String> para) {

        ResponseEntity<T2> responseEntity = null;
        T1 currentObject = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<T1> httpEntity = new HttpEntity<T1>(entityT1, httpHeaders);
            responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, entityT2, para);

            T2 responseResult = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(responseResult);

            currentObject = (T1) ConvertData.extraUserData(result.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return (T2) currentObject;
    }
}
