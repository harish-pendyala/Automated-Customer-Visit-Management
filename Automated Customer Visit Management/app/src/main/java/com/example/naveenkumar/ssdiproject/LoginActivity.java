package com.example.naveenkumar.ssdiproject;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements Login.LoginInterface{

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Sign IN");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_login);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userid = prefs.getString("userid","");
        String password = prefs.getString("password","");
        if (userid!=null && password!=null && userid.length()>0 && password.length()>0){
            String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/Login";
            progressDialog = ProgressDialog.show(this,null,"Please wait",true,false);
            new Login(LoginActivity.this).execute(url,userid,password);
        }

        TextView signUp = (TextView)findViewById(R.id.signUpText);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUPActivity.class);
                startActivity(intent);
            }
        });

        Button login = (Button)findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText useridText = (EditText)findViewById(R.id.userName);
                EditText passwordText = (EditText)findViewById(R.id.password);

                String userid = useridText.getText().toString();
                String password = passwordText.getText().toString();

                if (userid == null || password == null || userid.length() == 0 || password.length() < 8 ){
                    Toast.makeText(LoginActivity.this,"Please enter valid details",Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userid",userid);
                    editor.putString("password",password);
                    editor.apply();
                    String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/Login";
                    progressDialog = ProgressDialog.show(LoginActivity.this,null,"Please wait",true,false);
                    new Login(LoginActivity.this).execute(url,userid,password);
                }
            }
        });
    }

    @Override
    public void onLoginResponse(String response) {
        progressDialog.dismiss();
        if (response!=null && !response.equals("Failure")){
            ((EditText)findViewById(R.id.userName)).setText("");
            ((EditText)findViewById(R.id.password)).setText("");

            Intent intent = new Intent(LoginActivity.this,AppointmentsActivity.class);
            if (response.equals("Success")){
                intent.putExtra("isAppAvailable",false);
            }else if (response.equals("")){
                intent.putExtra("isAppAvailable",false);
            }else {
                intent.putExtra("isAppAvailable",true);
                intent.putExtra("data",response);
            }
            startActivity(intent);

        }else {
            Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
            ((EditText)findViewById(R.id.userName)).setText("");
            ((EditText)findViewById(R.id.password)).setText("");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("userid","");
            editor.putString("password","");
            editor.commit();
        }
    }
}
