package com.example.naveenkumar.ssdiproject;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by naveenkumar on 11/17/16.
 */
public class SignUp extends AsyncTask<String,Void,String>{

    SignUPActivity activity;
    public SignUp(SignUPActivity activity){
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        activity.onSignUpResponse(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];

        String firstname = strings[1];
        String lastname = strings[2];
        String mailid = strings[3];
        String userid = strings[5];
        String phone = strings[4];
        String password = strings[6];

        try {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("firstname", firstname)
                    .add("lastname",lastname)
                    .add("mailid",mailid)
                    .add("mobileno",phone)
                    .add("userid",userid)
                    .add("password",password)
                    .build();
            Request request = new Request.Builder()
                    .url(urlString)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseString = response.body().string();
            return responseString;
        }catch (Exception e){

        }

        return "";
    }

    interface SignUpInterface{
        void onSignUpResponse(String response);
    }
}
