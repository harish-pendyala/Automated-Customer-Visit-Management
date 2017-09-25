package com.example.naveenkumar.ssdiproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class BanksListActivity extends AppCompatActivity {

    ListView banksList;
    ArrayList<Bank> banks = new ArrayList<>();
    BanksAdapter banksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Select Branch");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_banks_list);
        banksList = (ListView)findViewById(R.id.listViewBanks);

        banksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bank bank = banks.get(i);
                Intent intent = new Intent(BanksListActivity.this,NormalBookingActivity.class);
                intent.putExtra("Bank",bank);
                startActivityForResult(intent,1001);
            }
        });

        String data = getIntent().getExtras().getString("Banks");
        parseBanks(data);
    }

    void parseBanks(String response){
        banks = new Gson().fromJson(response, new TypeToken<ArrayList<Bank>>(){}.getType());
        banksAdapter = new BanksAdapter(this,R.layout.bankitem,banks);
        banksList.setAdapter(banksAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED){
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }
}
