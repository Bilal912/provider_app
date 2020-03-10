package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.R;
import com.example.bus_reservation.volley.CustomRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class view_service extends AppCompatActivity {
    TextInputEditText title,incall,outcall;
    EditText descrip;
    Spinner spinner;
    TextView back;
    ElasticButton elasticButton;
    ArrayList<String> name;
    ArrayList<String> c_id;
    String Title,Incall,Outcall,Description,cat_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);

        String statu = getIntent().getStringExtra("status");

        name= new ArrayList<>();
        c_id=new ArrayList<>();

        spinner=findViewById(R.id.spinner_id);
        Title=getIntent().getStringExtra("title");
        Incall=getIntent().getStringExtra("incall");
        Outcall=getIntent().getStringExtra("outcall");
        final String Service_id=getIntent().getStringExtra("service_id");
        Description=getIntent().getStringExtra("description");
        cat_name=getIntent().getStringExtra("cat_name");

        getspinnerdata(cat_name);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title=findViewById(R.id.title);
        incall=findViewById(R.id.incall);
        outcall=findViewById(R.id.outcall);
        descrip=findViewById(R.id.descript_id);
        elasticButton=findViewById(R.id.update);

        title.setText(Title);
        incall.setText(Incall);
        outcall.setText(Outcall);
        descrip.setText(Description);

        if (statu.equals("edit")){
            descrip.setHeight(400);

            elasticButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(title.getText().toString())){
                        Toast.makeText(view_service.this, "Please Enter Title", LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(incall.getText().toString())){
                        Toast.makeText(view_service.this, "Please Enter Incall Rate", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(outcall.getText().toString())){
                        Toast.makeText(view_service.this, "Please Enter Outcall Rate", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(descrip.getText().toString())){
                        Toast.makeText(view_service.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        int one = (int) spinner.getSelectedItemId();
                        String two = c_id.get(one);
                        udapte(Service_id, two,title.getText().toString().trim(),incall.getText().toString().trim()
                        ,outcall.getText().toString().trim(),descrip.getText().toString());
                    }
                }
            });
        }
        else {
            title.setEnabled(false);
            outcall.setEnabled(false);
            incall.setEnabled(false);
            descrip.setEnabled(false);
            spinner.setEnabled(false);
            elasticButton.setVisibility(View.GONE);
        }
    }

    private void udapte(String serviceId, String two, String title, String incall, String outcall, String des) {
        final AlertDialog loading = new ProgressDialog(view_service.this);
        loading.setMessage("Checking...");
        loading.show();

        Map<String, String> params = new Hashtable<String, String>();

        params.put("update_service", "true");
        params.put("service_id", serviceId);
        params.put("catid", two);
        params.put("title", title);
        params.put("description", des);
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
                        Toast.makeText(view_service.this, response.getString("data"), LENGTH_SHORT).show();
                        finish();
                    } else {
                        loading.dismiss();
                        Toast.makeText(view_service.this, "Service Not Updated", LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    loading.dismiss();
                    makeText(view_service.this, "Something Went Wrong", LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(view_service.this, "Something went wrong" + error, LENGTH_LONG).show();
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
        RequestQueue queue = Volley.newRequestQueue(view_service.this);
        queue.add(jsonRequest);

    }

    private void getspinnerdata(final String cat_name) {

        final android.app.AlertDialog loading = new ProgressDialog(view_service.this);
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
                        spinner.setAdapter(new ArrayAdapter<String>(view_service.this, android.R.layout.simple_spinner_dropdown_item, name));

                        for (int j=0;j<name.size();j++){
                            if (cat_name.equals(name.get(j))){
                                spinner.setSelection(j);
                            }
                        }

                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(view_service.this,"No Category found", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(view_service.this,"Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(view_service.this, "Connection Error", Toast.LENGTH_LONG).show();
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

        RequestQueue queue = Volley.newRequestQueue(view_service.this);
        queue.add(jsonRequest);

    }
}
