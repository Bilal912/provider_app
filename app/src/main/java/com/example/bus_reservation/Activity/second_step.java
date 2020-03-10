package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bus_reservation.R;
import com.skydoves.elasticviews.ElasticButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class second_step extends AppCompatActivity {
TextView back;
private DatePickerDialog.OnDateSetListener mDateSetListener;
ElasticButton elasticButton;
String name,tag,email,username,phone,password,zip,distance,gender;
EditText venmo,cash_app,deposit,dob,height,ethnicity,hair,service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_step);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        name=getIntent().getStringExtra("name");
        tag=getIntent().getStringExtra("tag");
        email=getIntent().getStringExtra("email");
        username=getIntent().getStringExtra("username");
        gender=getIntent().getStringExtra("gender");
        phone=getIntent().getStringExtra("phone");
        zip=getIntent().getStringExtra("zip");
        password=getIntent().getStringExtra("password");
        distance=getIntent().getStringExtra("distance");

        venmo=findViewById(R.id.venmo_id);
        cash_app=findViewById(R.id.cash_app);
        deposit=findViewById(R.id.deposite);
        dob=findViewById(R.id.dob);
        height=findViewById(R.id.height);
        ethnicity=findViewById(R.id.ethnicity);
        hair=findViewById(R.id.hair);
        service=findViewById(R.id.service);

        elasticButton=findViewById(R.id.next);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        second_step.this,
                        mDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                dob.setText(date);
            }
        };

        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(venmo.getText().toString())){
                    makeText(second_step.this, "Venmo ID is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(cash_app.getText().toString())){
                    makeText(second_step.this, "Cash App is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(deposit.getText().toString())){
                    makeText(second_step.this, "Deposite is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(dob.getText().toString())){
                    makeText(second_step.this, "Date Of Birth is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(height.getText().toString())){
                    makeText(second_step.this, "Height is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(ethnicity.getText().toString())){
                    makeText(second_step.this, "Ethnicity is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(service.getText().toString())){
                    makeText(second_step.this, "Service is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(hair.getText().toString())){
                    makeText(second_step.this, "Hair Color is required", LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(second_step.this, last_step.class);
                    intent.putExtra("name", name);
                    intent.putExtra("tag", tag);
                    intent.putExtra("email", email);
                    intent.putExtra("username", username);
                    intent.putExtra("gender", gender);
                    intent.putExtra("phone", phone);
                    intent.putExtra("zip", zip);
                    intent.putExtra("password", password);
                    intent.putExtra("distance", distance);
                    intent.putExtra("venom",venmo.getText().toString());
                    intent.putExtra("cash",cash_app.getText().toString());
                    intent.putExtra("deposite",deposit.getText().toString());
                    intent.putExtra("dob",dob.getText().toString());
                    intent.putExtra("height",height.getText().toString());
                    intent.putExtra("ethnicity",ethnicity.getText().toString());
                    intent.putExtra("hair",hair.getText().toString());
                    intent.putExtra("services",service.getText().toString());
                    startActivity(intent);

                }

            }
        });
    }

}
