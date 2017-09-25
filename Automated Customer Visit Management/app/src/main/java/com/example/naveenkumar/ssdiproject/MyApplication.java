package com.example.naveenkumar.ssdiproject;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

/**
 * Created by naveenkumar on 11/21/16.
 */
public class MyApplication extends Application {

    private BeaconManager beaconManager;
    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("myRegion", UUID.fromString("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6"),null,null));
            }
        });
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                for (Beacon beacon:list)
                    Log.d("Beacon","Beacons");
            }

            @Override
            public void onExitedRegion(Region region) {
                Log.d("Enter:",region.getMajor() +" "+region.getMinor());
            }
        });
    }
}
