package com.example.naveenkumar.ssdiproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

public class BeaconBranchActivity extends AppCompatActivity implements BookingForBeacon.BookingForBeaconInterface{

    ProgressDialog progressDialog;
    Bank bank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Branch Region");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_beacon_branch);
        bank = (Bank) getIntent().getExtras().getSerializable("Bank");
        TextView title = (TextView)findViewById(R.id.bbranchTitle);
        title.setText("You are at : "+bank.getBranchname()+", "+bank.getBranchcity());
        final String[] type = {""};
        final RadioGroup group = (RadioGroup) findViewById(R.id.beaconmenuGroup);

        Button button = (Button) findViewById(R.id.bbookButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = group.getCheckedRadioButtonId();
                if (id == R.id.brb1){
                    type[0] = "Deposit";
                }else if (id == R.id.brb2){
                    type[0] = "Withdrawls";
                }else if (id == R.id.brb3){
                    type[0] = "Cheques";
                }else if (id == R.id.brb4){
                    type[0] = "Others";
                }
                progressDialog = ProgressDialog.show(BeaconBranchActivity.this,null,"Please wait",true,false);
                String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/case1";
                String bankJSON = new Gson().toJson(bank);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userid = prefs.getString("userid","");
                new BookingForBeacon(BeaconBranchActivity.this).execute(url,bankJSON,userid);
            }
        });
    }

    @Override
    public void onBeaconBookingResponse(String response) {
        progressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(BeaconBranchActivity.this);
        builder.setTitle("Booking Message").setMessage(response).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }
}
