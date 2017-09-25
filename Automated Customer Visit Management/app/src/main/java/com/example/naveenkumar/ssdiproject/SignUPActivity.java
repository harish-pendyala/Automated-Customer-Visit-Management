package com.example.naveenkumar.ssdiproject;

import android.app.ProgressDialog;
import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUPActivity extends AppCompatActivity implements SignUp.SignUpInterface{

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Sign UP");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_sign_up);

        Button button1 = (Button)findViewById(R.id.sbuttonCancel);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button button = (Button)findViewById(R.id.sbuttonSignUp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = ((EditText)findViewById(R.id.sfirstname)).getText().toString().trim();
                String lastname = ((EditText)findViewById(R.id.slastname)).getText().toString().trim();
                String mailid = ((EditText)findViewById(R.id.semailid)).getText().toString().trim();
                String userid = ((EditText)findViewById(R.id.sloginid)).getText().toString().trim();
                String mobileno = ((EditText)findViewById(R.id.sphoneno)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.spassword)).getText().toString();
                String retypePassword = ((EditText)findViewById(R.id.srpassword)).getText().toString();

                if (firstname.isEmpty() || lastname.isEmpty() || mailid.isEmpty() || userid.isEmpty() || mobileno.isEmpty() || password.isEmpty() ||retypePassword.isEmpty() || userid.length()<6 || password.length()<8 || !password.equals(retypePassword)){
                    Toast.makeText(SignUPActivity.this,"Please enter valid details",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog = ProgressDialog.show(SignUPActivity.this,null,"Please wait",true,false);
                    String url = "http://"+ConnDetails.ip+":8080/ACVM/Controller/SignUP";
                    new SignUp(SignUPActivity.this).execute(url,firstname,lastname,mailid,mobileno,userid,password);
                }
            }
        });
    }

    @Override
    public void onSignUpResponse(String response) {
        progressDialog.dismiss();
        if (response.equals("Success")){
            Toast.makeText(this,"Successfully Signed up",Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this,"Signup failed",Toast.LENGTH_SHORT).show();
            ((EditText)findViewById(R.id.sfirstname)).setText("");
            ((EditText)findViewById(R.id.slastname)).setText("");
            ((EditText)findViewById(R.id.semailid)).setText("");
            ((EditText)findViewById(R.id.sloginid)).setText("");
            ((EditText)findViewById(R.id.sphoneno)).setText("");
            ((EditText)findViewById(R.id.spassword)).setText("");
            ((EditText)findViewById(R.id.srpassword)).setText("");
        }
    }
}
