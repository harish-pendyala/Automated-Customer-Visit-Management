package com.example.naveenkumar.ssdiproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NormalBookingActivity extends AppCompatActivity implements GetTimeSlots.GetTimeSlotsInterface,NormalBooking.NormalBookingInterface{

    Spinner spinner;
    LinearLayout layout;
    TextView textView;
    String selectedTime = "";
    ArrayList<String> slots = new ArrayList<>();
    ProgressDialog progressDialog;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Book Appointment");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_normal_booking);
        spinner = (Spinner)findViewById(R.id.dateSpinner);
        layout = (LinearLayout)findViewById(R.id.buttonLayout);
        textView = (TextView) findViewById(R.id.textView5);

        final Bank bank = (Bank) getIntent().getExtras().getSerializable("Bank");
        spinner.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        TextView branchText = (TextView)findViewById(R.id.nbrancname);
        branchText.setText("You Selected: "+bank.getBranchname()+", "+bank.getBranchcity());
        final EditText dateText = (EditText)findViewById(R.id.dateField);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateText.setText(dateFormatter.format(newDate.getTime()));
                selectedTime = "";
                spinner.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                sendBankDetailsAndDate(bank,dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        ImageView dateButton = (ImageView)findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        Button book = (Button)findViewById(R.id.nBook);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userid = prefs.getString("userid","");
                progressDialog = ProgressDialog.show(NormalBookingActivity.this,null,"Please wait",true,false);
                String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/case3";
                new NormalBooking(NormalBookingActivity.this).execute(url,new Gson().toJson(bank),dateText.getText().toString(),selectedTime,userid);
            }
        });
    }

    void sendBankDetailsAndDate(Bank bank, String date){
        progressDialog = ProgressDialog.show(NormalBookingActivity.this,"Please wait","Getting Avaiable slots",true,false);
        String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/case2";
        new GetTimeSlots(NormalBookingActivity.this).execute(url,new Gson().toJson(bank),date);
    }


    @Override
    public void onTimeSlotsResponse(String response) {
        progressDialog.dismiss();
        if (response.equals("No slots available on this day")){
            Toast.makeText(NormalBookingActivity.this,"No slots available on this day",Toast.LENGTH_SHORT).show();
        }else {
            slots = new Gson().fromJson(response, new TypeToken<ArrayList<String>>(){}.getType());
            Log.d("Times",slots.size()+"");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, slots);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            textView.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            layout.setVisibility(View.VISIBLE);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedTime = slots.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    @Override
    public void onNormalBookingResponse(String response) {
        progressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(NormalBookingActivity.this);
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
}
