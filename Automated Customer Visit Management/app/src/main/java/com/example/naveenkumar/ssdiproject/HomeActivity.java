package com.example.naveenkumar.ssdiproject;

import com.estimote.sdk.Region;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements BankByBeacon.BankByBeaconInterface, AllBanks.AllBankInterface{

    private BeaconManager beaconManager;
    private Region region;

    boolean isBeaconFound = false;
    boolean bankDetailsFlag = false;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressDialog = ProgressDialog.show(this,null,"Please wait",true,false);
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    if (!isBeaconFound) {
                        Beacon nearestBeacon = list.get(0);
                        getBranchDetailsByBeaconUUID(nearestBeacon);
                        isBeaconFound = true;
                    }
                }else {
                    if (!bankDetailsFlag){
                        getAllBranchDetails();
                        bankDetailsFlag = false;
                    }
                }
            }
        });
        region = new Region("ranged region", UUID.fromString("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6"), null, null);
    }

    void getAllBranchDetails(){
        String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/allbanks";
        new AllBanks(HomeActivity.this).execute(url);

    }

    void getBranchDetailsByBeaconUUID(Beacon beacon){
        Log.d("Details",beacon.getProximityUUID().toString()+beacon.getMajor()+" "+beacon.getMinor());
        String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/BankByBeacon";
        new BankByBeacon(HomeActivity.this).execute(url,beacon.getProximityUUID().toString(),beacon.getMajor()+"",beacon.getMinor()+"");
        beaconManager.stopRanging(region);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }

    @Override
    public void bankByBeaconDetails(String S) {
        progressDialog.dismiss();
        Bank bank = new Gson().fromJson(S,Bank.class);
        Intent intent = new Intent(HomeActivity.this,BeaconBranchActivity.class);
        intent.putExtra("Bank",bank);
        startActivityForResult(intent,1001);
    }

    @Override
    public void allBanksDetails(String S) {
        progressDialog.dismiss();
        Intent intent = new Intent(HomeActivity.this,BanksListActivity.class);
        intent.putExtra("Banks",S);
        startActivityForResult(intent,1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED){
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        }
    }
}
