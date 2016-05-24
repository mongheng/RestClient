package com.earmongheng.restclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etPhone;
    private Button btnSave;
    private Button btnCancel;

    private String url = "http://192.168.1.138:8080/Realestate/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(etUsername.getText().toString(), etPassword.getText().toString(), etEmail.getText().toString(), etPhone.getText().toString());
                //new SaveAsyncTask().execute(url + "save/", etUsername.getText().toString(), etPassword.getText().toString(), etEmail.getText().toString(), etPhone.getText().toString());
                Toast.makeText(getBaseContext(), "Save Data Successfully", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private class SaveAsyncTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... params) {

            User user = new User(params[1], params[2], params[3], params[4]);

            postData(params[0],user);

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
        }

        private void postData(String url, User user) {

            try {

                List<BasicNameValuePair> users = new ArrayList<BasicNameValuePair>();
                users.add(new BasicNameValuePair("userid",String.valueOf(user.getUserid())));
                users.add(new BasicNameValuePair("username",user.getUsername()));
                users.add(new BasicNameValuePair("password",user.getPassword()));
                users.add(new BasicNameValuePair("telephone",user.getTelephone()));
                users.add(new BasicNameValuePair("email",user.getEmail()));

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(users);
                entity.setContentEncoding(HTTP.UTF_8);
                httpPost.setEntity(entity);

                HttpResponse httpResponse = httpClient.execute(httpPost);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
