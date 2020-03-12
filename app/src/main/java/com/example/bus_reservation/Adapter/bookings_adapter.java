package com.example.bus_reservation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bus_reservation.Activity.view_booking;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.Model.bookings_model;
import com.example.bus_reservation.Model.membership_model;
import com.example.bus_reservation.R;
import com.example.bus_reservation.private_gallery;
import com.example.bus_reservation.reload_data;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class bookings_adapter extends RecyclerView.Adapter<bookings_adapter.GithubViewHolder> {
    private Context context;
    private ArrayList<bookings_model> data;
    private reload_data reload;
    public bookings_adapter(Context context, ArrayList<bookings_model> data,reload_data reload) {
        this.context=context;
        this.data=data;
        this.reload=reload;
    }

    @NonNull
    @Override
    public bookings_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.booking_sample,parent,false);
        return new bookings_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder holder, final int position) {

        holder.name.setText(data.get(position).getClient_name());
        holder.s_name.setText("Service Name : ".concat(data.get(position).getService_name()));
        holder.s_type.setText("Service Type : ".concat(data.get(position).getService_type()));
        holder.date.setText("Date : ".concat(data.get(position).getStart_date()));
        holder.time.setText("Time : ".concat(data.get(position).getStart_time()));
        holder.Hour.setText("Total Hour : ".concat(data.get(position).getHours()));
        holder.hourly_price.setText("Hourly Price : ".concat(data.get(position).getHourly_price()));
        holder.tprice.setText(data.get(position).getTitle_price());

        if (data.get(position).getHotel_room().equals("null")){
            holder.location.setText("Location : ".concat(data.get(position).getLocation_type()
            .concat(" , "+data.get(position).getAddress())));
        }
        else {
            holder.location.setText("Location : ".concat(data.get(position).getLocation_type()+" : Room No "+data.get(position).getHotel_room()
                    .concat(", "+data.get(position).getAddress())));
        }
        if (data.get(position).getAccepted().equals("1")){
            holder.Accept.setText("Accepted");
            holder.Accept.setTextSize(9);
            holder.Accept.setTextColor(Color.parseColor("#fea984"));
        }
        else {
            holder.cancel.setVisibility(View.GONE);
            holder.Accept.setText("Accept");
        }

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.Accept.getText().equals("Accept")){

                    final android.app.AlertDialog loading = new ProgressDialog(context);
                    loading.setMessage("Wait...");
                    loading.show();
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_booking_accept+data.get(position).getBookingid(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Boolean status = null;
                            try {
                                status = response.getBoolean("response");
                                if (status) {
                                    Toast.makeText(context,response.getString("data"),Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                    reload.reload_data();
                                }
                                else {
                                    Toast.makeText(context,"Data not Accepted",Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                }
                            }
                            catch (Exception e){
                                loading.dismiss();
                                Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                            , new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
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
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(jsonRequest);

                }
                else {
                    //do nothing
                }

            }
        });
        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, view_booking.class);
                intent.putExtra("booking_id",data.get(position).getBookingid());
                context.startActivity(intent);
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SweetAlertDialog pdial = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
                pdial.setContentText("You want to cancel?");
                pdial.setCancelable(false);
                pdial.setConfirmText("Yes");
                pdial.setCancelText("No");
                pdial.show();

                pdial.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        pdial.dismiss();

                        final android.app.AlertDialog loading = new ProgressDialog(context);
                        loading.setMessage("Wait...");
                        loading.show();
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_booking_delete+data.get(position).getBookingid(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Boolean status = null;
                                try {
                                    status = response.getBoolean("response");
                                    if (status) {
                                        loading.dismiss();
                                        reload.reload_data();
                                    }
                                    else {
                                        loading.dismiss();
                                        Toast.makeText(context,"Booking not canceled", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e){
                                    loading.dismiss();
                                    Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loading.dismiss();
                                Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
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

                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(jsonRequest);
                    }
                });

                pdial.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pdial.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    // Clean all elements of the recycler
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<bookings_model> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder {
        ElasticButton View,Accept;
        TextView name,location,hourly_price,tprice,Hour,date,time,s_name,s_type,cancel;
        public GithubViewHolder(View view) {
            super(view);
            cancel=view.findViewById(R.id.cancel);
            View=view.findViewById(R.id.View);
            Accept=view.findViewById(R.id.accept);
            name=view.findViewById(R.id.name);
            location=view.findViewById(R.id.location);
            hourly_price=view.findViewById(R.id.hourly_price);
            tprice=view.findViewById(R.id.total_price);
            Hour=view.findViewById(R.id.hour);
            date=view.findViewById(R.id.date);
            time=view.findViewById(R.id.time);
            s_name=view.findViewById(R.id.sevice_name);
            s_type=view.findViewById(R.id.service_type);

        }
    }
}
