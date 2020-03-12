package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.Model.bookings_model;
import com.example.bus_reservation.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class view_booking extends AppCompatActivity {
TextView back,Name,special;
ImageView imageView;
TextInputEditText service,service_type,date,address,hour,hour_price,t_price;
String image_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView=findViewById(R.id.client_image);
        Name=findViewById(R.id.client_name);
        special=findViewById(R.id.client_request);
        service=findViewById(R.id.service);
        service_type=findViewById(R.id.service_type);
        address=findViewById(R.id.address);
        hour=findViewById(R.id.hour);
        hour_price=findViewById(R.id.hourly_price);
        date=findViewById(R.id.date_time);
        t_price=findViewById(R.id.total_price);

        service_type.setEnabled(false);
        service.setEnabled(false);
        address.setEnabled(false);
        date.setEnabled(false);
        hour_price.setEnabled(false);
        hour.setEnabled(false);
        t_price.setEnabled(false);


        String booking_id = getIntent().getStringExtra("booking_id");

        getData(booking_id);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view_booking.this,Show_image.class);
                intent.putExtra("image",image_name);
                startActivity(intent);

            }
        });
    }

    private void getData(String booking_id) {
        final android.app.AlertDialog loading = new ProgressDialog(view_booking.this);
        loading.setMessage("Getting Information...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_booking_view+booking_id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            image_name =  Constant.booking_image_url.concat(object.getString("id_image"));
                            Picasso.with(view_booking.this)
                                    .load(image_name)
                                    .into(imageView);

                            Name.setText(object.getString("client_name"));
                            special.setText(object.getString("special_request"));
                            service.setText(object.getString("service_title"));
                            service_type.setText(object.getString("service_type"));
                            date.setText(object.getString("date_added"));
                            if (object.getString("hotel_room_no").equals("null")){
                                address.setText(object.getString("location_type").concat(" , "+object.getString("client_address")));
                            }
                            else {
                                address.setText(object.getString("location_type").concat(" ,Room No : "+object.getString("hotel_room_no"))
                                        .concat(" , "+object.getString("client_address")));
                            }
                            hour.setText(object.getString("hours"));
                            hour_price.setText(object.getString("hourly_price"));
                            t_price.setText(object.getString("total_price"));

                        }

                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(view_booking.this,"No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(view_booking.this,"Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(view_booking.this, "Connection Error", Toast.LENGTH_LONG).show();
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

        RequestQueue queue = Volley.newRequestQueue(view_booking.this);
        queue.add(jsonRequest);

    }
}
