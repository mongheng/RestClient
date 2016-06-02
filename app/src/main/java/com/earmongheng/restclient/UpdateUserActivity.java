package com.earmongheng.restclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.earmongheng.restclient.models.User;
import com.earmongheng.restclient.utility.ConvertData;

public class UpdateUserActivity extends AppCompatActivity {

    private Button btnUpdate;
    private Button btnCancel;
    private EditText etUpdateUserName;
    private EditText etUpdatePassword;
    private EditText etUpdateTelephone;
    private EditText etUpdateEmail;
    private User user;
    private String url = "http://192.168.1.138:8080/Realestate/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);

        etUpdateUserName = (EditText) findViewById(R.id.etUpdateUserName);
        etUpdatePassword = (EditText) findViewById(R.id.etUpdatePassword);
        etUpdateTelephone = (EditText) findViewById(R.id.etUpdateTelephone);
        etUpdateEmail = (EditText) findViewById(R.id.etUpdateEmail);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        user = (User) getIntent().getExtras().getSerializable("user");
        new GetUserByIdAsyncTask().execute(url + "select/" + user.getUserid());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = new User();
                currentUser.setPassword(etUpdatePassword.getText().toString());
                currentUser.setEmail(etUpdateEmail.getText().toString());
                currentUser.setTelephone(etUpdateTelephone.getText().toString());
                currentUser.setUsername(etUpdateUserName.getText().toString());
                new UpdateUserAsyncTask(currentUser).execute(url + "update/" + user.getUserid());
            }
        });
    }

    private class GetUserByIdAsyncTask extends AsyncTask<String,Void,User> {

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user != null) {
                etUpdateEmail.setText(user.getEmail());
                etUpdatePassword.setText(user.getPassword());
                etUpdateTelephone.setText(user.getTelephone());
                etUpdateUserName.setText(user.getUsername());
            }
        }

        @Override
        protected User doInBackground(String... params) {
            String result = ConvertData.GET(params[0]);
            User user = ConvertData.extraUserData(result);
            if (user != null) {
                return user;
            }else {
                return null;
            }
        }
    }

    private class UpdateUserAsyncTask extends AsyncTask<String,Void,User> {

        private User m_user;

        public UpdateUserAsyncTask(User m_user) {
            this.m_user = m_user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            Toast.makeText(getBaseContext(),"Update Successful",Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        protected User doInBackground(String... params) {
            User currentUser = (User) ConvertData.getHttpResponseObject(params[0],(Object) m_user,Object.class,null);
            return currentUser;
        }
    }
}
