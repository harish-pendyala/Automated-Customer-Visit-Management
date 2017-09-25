package com.example.naveenkumar.ssdiproject;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by naveenkumar on 11/29/16.
 */
public class BookingForBeacon extends AsyncTask<String, Void, String> {
    BeaconBranchActivity activity;

    public BookingForBeacon(BeaconBranchActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];

        String bankdetails = strings[1];
        String userid = strings[2];


        try {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("bankdetails", bankdetails)
                    .add("userid",userid)
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        activity.onBeaconBookingResponse(s);
    }

    interface BookingForBeaconInterface{
        void onBeaconBookingResponse(String response);
    }
}
