package com.example.naveenkumar.ssdiproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AppointmentsActivity extends AppCompatActivity implements GetAppointments.AppointmentsInterface{

    AppointmentsAdapters appointmentsAdapters;
    TextView messageText;
    ListView listView;
    ArrayList<Appointment> appointments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Appointments");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_appointments);

        String data = getIntent().getExtras().getString("data");

        listView = (ListView)findViewById(R.id.appListView);
        messageText = (TextView)findViewById(R.id.messageText);

        appointmentsAdapters = new AppointmentsAdapters(this,R.layout.appointmentitem,appointments);
        listView.setAdapter(appointmentsAdapters);

        boolean flag = getIntent().getExtras().getBoolean("isAppAvailable");
        if (flag){
            messageText.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            parseAppointments(data);
        }else {
            messageText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }

        ImageView add = (ImageView) findViewById(R.id.addAppointment);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentsActivity.this,HomeActivity.class);
                startActivityForResult(intent,1001);
            }
        });
    }

    void parseAppointments(String response){
        appointments = new Gson().fromJson(response, new TypeToken<ArrayList<Appointment>>(){}.getType());
        appointmentsAdapters = new AppointmentsAdapters(this,R.layout.appointmentitem,appointments);
        listView.setAdapter(appointmentsAdapters);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String userid = prefs.getString("userid","");
            String password = prefs.getString("password","");
            String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/Login";
            new GetAppointments(AppointmentsActivity.this).execute(url,userid,password);
        }
    }

    @Override
    public void onAppointmentResponse(String response) {
        if (response.equals("Success") || response.equals("")){
            messageText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else if (response.equals("Failure")){

        }else {
            messageText.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            parseAppointments(response);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppointmentsActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userid","");
                editor.putString("password","");
                editor.commit();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

    }
}
