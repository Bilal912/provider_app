package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Adapter.service_adapter;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.Dashboard;
import com.example.bus_reservation.Model.service_model;
import com.example.bus_reservation.R;
import com.example.bus_reservation.reload_data;
import com.example.bus_reservation.volley.CustomRequest;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class add_sprovid extends AppCompatActivity {
ElasticButton elasticButton;
ArrayList<String> name,c_id;
EditText title,incall,outcall,description;
Spinner spinner;
TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sprovid);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        name=new ArrayList<>();
        c_id=new ArrayList<>();
        elasticButton=findViewById(R.id.submit_id);
        title=findViewById(R.id.title);
        incall=findViewById(R.id.incall_id);
        outcall=findViewById(R.id.outcall_id);
        description=findViewById(R.id.descript_id);
        spinner=findViewById(R.id.spinner_id);

        getspinnerdata();

        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(title.getText().toString())){
                    Toast.makeText(add_sprovid.this, "Please Enter Title", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(incall.getText().toString())){
                    Toast.makeText(add_sprovid.this, "Please Enter Incall Rate", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(outcall.getText().toString())){
                    Toast.makeText(add_sprovid.this, "Please Enter Outcall Rate", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(description.getText().toString())){
                    Toast.makeText(add_sprovid.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                }
                else {
                    int one = (int) spinner.getSelectedItemId();
                    String two = c_id.get(one);

                    SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
                    final String id = editors.getString("id","Null");

                    getData(title.getText().toString().trim(),incall.getText().toString().trim(),
                    outcall.getText().toString().trim(),description.getText().toString().trim(),two,id);

                }
            }
        });
    }

    private void getspinnerdata() {

        final android.app.AlertDialog loading = new ProgressDialog(add_sprovid.this);
        loading.setMessage("Wait...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_get_category, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;

                try {
                    status = response.getBoolean("response");
                    JSONArray jsonArray = response.getJSONArray("data");
                    if (status) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                        name.add(object.getString("name"));
                        c_id.add(object.getString("catid"));
                        }
                        spinner.setAdapter(new ArrayAdapter<String>(add_sprovid.this, android.R.layout.simple_spinner_dropdown_item, name));

                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(add_sprovid.this,"No Category found", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(add_sprovid.this,"Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(add_sprovid.this, "Connection Error", Toast.LENGTH_LONG).show();
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

        RequestQueue queue = Volley.newRequestQueue(add_sprovid.this);
        queue.add(jsonRequest);

    }
    private void getData(String title, String incall, String outcall, String desc, String cat_id, String id) {
        final AlertDialog loading = new ProgressDialog(add_sprovid.this);
        loading.setMessage("Checking...");
        loading.show();

        Map<String, String> params = new Hashtable<String, String>();
        params.put("add_service", "true");
        params.put("provider_id", id);
        params.put("catid", cat_id);
        params.put("title", title);
        params.put("description", desc);
        params.put("incall_rate", incall);
        params.put("outcall_rate", outcall);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");

                    if (status) {

                        loading.dismiss();

                        Toasty.success(add_sprovid.this,"Data Inserted ,Refreash your page !", Toasty.LENGTH_LONG,true).show();
                        onBackPressed();
                    } else {
                        loading.dismiss();
                        Toast.makeText(add_sprovid.this, "Service Not Submitted", LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    loading.dismiss();
                    makeText(add_sprovid.this, "Something Went Wrong", LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(add_sprovid.this, "Something went wrong" + error, LENGTH_LONG).show();
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
        RequestQueue queue = Volley.newRequestQueue(add_sprovid.this);
        queue.add(jsonRequest);
    }
    public void onBackPressed(){
        finish();
    }
}
