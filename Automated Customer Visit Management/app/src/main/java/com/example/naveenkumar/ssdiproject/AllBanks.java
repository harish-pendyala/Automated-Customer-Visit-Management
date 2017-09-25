package com.example.naveenkumar.ssdiproject;

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
public class AllBanks extends AsyncTask<String, Void, String> {
    HomeActivity activity;

    public AllBanks(HomeActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];

        try {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlString)
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
        activity.allBanksDetails(s);
    }

    interface AllBankInterface {
        void allBanksDetails(String S);
    }
}
