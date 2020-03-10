package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.R;
import com.example.bus_reservation.volley.CustomRequest;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class last_step extends AppCompatActivity {
TextView back;
ElasticButton elasticButton;
    String name,tag,email,username,phone,password,zip,distance,gender,venmo,cash_app,deposit,dob,height,ethnicity,hair,service;
    EditText insta,twiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_step);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        elasticButton=findViewById(R.id.last_step);
        name=getIntent().getStringExtra("name");
        tag=getIntent().getStringExtra("tag");
        email=getIntent().getStringExtra("email");
        username=getIntent().getStringExtra("username");
        gender=getIntent().getStringExtra("gender");
        phone=getIntent().getStringExtra("phone");
        zip=getIntent().getStringExtra("zip");
        password=getIntent().getStringExtra("password");
        distance=getIntent().getStringExtra("distance");

        venmo=getIntent().getStringExtra("venom");
        cash_app=getIntent().getStringExtra("cash");
        deposit=getIntent().getStringExtra("deposite");
        dob=getIntent().getStringExtra("dob");
        height=getIntent().getStringExtra("height");
        ethnicity=getIntent().getStringExtra("ethnicity");
        hair=getIntent().getStringExtra("hair");
        service=getIntent().getStringExtra("services");

        insta=findViewById(R.id.instagram);
        twiter=findViewById(R.id.twitter);

        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(insta.getText().toString())){
                    makeText(last_step.this, "Instagram is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(twiter.getText().toString())){
                    makeText(last_step.this, "Twitter is required", LENGTH_SHORT).show();
                }
                else {
                    getData(name,tag,email,username,phone,password,zip,distance,gender,venmo,cash_app,deposit,dob
                            ,height,ethnicity,hair,service,insta.getText().toString(),twiter.getText().toString());
                }
            }
        });
    }

    private void getData(String name, String tag, String email, String username, String phone, String password, String zip,
                         String distance, String gender, String venmo, String cash_app, String deposit, String dob,
                         String height, String ethnicity, String hair, String service, String Instag, String Twitt) {

        final android.app.AlertDialog loading = new ProgressDialog(this);
        loading.setMessage("Checking...");
        loading.show();

        Map<String, String> params = new Hashtable<String, String>();
        params.put("registration", "true");
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        params.put("name", name);
        params.put("phone",phone);
        params.put("age",dob);

        params.put("gender",gender);
        params.put("tagline",tag);
        params.put("zipcode",zip);
        params.put("distance",distance);
        params.put("venmo_id",venmo);
        params.put("cash_app_id",cash_app);
        params.put("deposit_percentage",deposit);
        params.put("height",height);
        params.put("ethnicity",ethnicity);
        params.put("hair_color",hair);
        params.put("instagram",Instag);
        params.put("twitter",Twitt);
        params.put("services",service);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Boolean status = null;
                String msg = "";

                try {
                    status = response.getBoolean("response");
                    if (status) {
                        loading.dismiss();
                        msg = response.getString("data");
                        Toast.makeText(last_step.this,msg, LENGTH_SHORT).show();
                        Intent intent = new Intent(last_step.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(last_step.this,"Registration Failed", LENGTH_SHORT).show();
                    }
                }

                catch (JSONException e) {
                    loading.dismiss();
                    makeText(last_step.this,"Something Went Wrong", LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(getApplicationContext(), "Connection Error" + error, LENGTH_SHORT).show();
            }
        });

        jsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonRequest);
}
}
