package com.earmongheng.restclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.earmongheng.restclient.helper.SaveTask;
import com.earmongheng.restclient.models.User;
import com.earmongheng.restclient.utility.ConvertData;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etPhone;
    private Button btnSave;
    private Button btnCancel;

    private String url = "http://192.168.1.138:8080/Realestate/save/";

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
                new SaveTask(user, null).execute(url);
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
}
