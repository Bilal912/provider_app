package com.example.bus_reservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Adapter.bookings_adapter;
import com.example.bus_reservation.Adapter.membership_adapter;
import com.example.bus_reservation.Adapter.service_adapter;
import com.example.bus_reservation.Model.bookings_model;
import com.example.bus_reservation.Model.membership_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;


public class bookings extends Fragment implements reload_data{
    RecyclerView recyclerView;
    ArrayList<bookings_model> model;
    private SwipeRefreshLayout swipeContainer;
    TextView textView;
    bookings_adapter bookings_adapter;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);


        textView=view.findViewById(R.id.no_file_id);
        recyclerView = view.findViewById(R.id.recycler_id);
        model = new ArrayList<>();
        SharedPreferences editors = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        id = editors.getString("id", "Null");

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                // To keep animation for 4 seconds
                getDataReload(id);
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeContainer.setRefreshing(false);
                    }
                }, 1000); // Delay in millis
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        getData(id);

        return view;
    }

    private void getDataReload(String id) {

//        final android.app.AlertDialog loading = new ProgressDialog(getContext());
//        loading.setMessage("Wait...");
//       loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_bookings + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    model.clear();
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            bookings_model value = new bookings_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            value.setBookingid(object.getString("bookingid"));
                            value.setClient_id(object.getString("client_id"));
                            value.setClient_name(object.getString("client_name"));
                            value.setService_name(object.getString("service_name"));
                            value.setService_type(object.getString("service_type"));
                            value.setStart_date(object.getString("start_date"));
                            value.setStart_time(object.getString("start_time"));
                            value.setTitle_price(object.getString("title_price"));
                            value.setLocation_type(object.getString("location_type"));
                            value.setAddress(object.getString("address"));
                            value.setHours(object.getString("hours"));
                            value.setHourly_price(object.getString("hourly_price"));
                            value.setId_image(object.getString("id_image"));
                            value.setAccepted(object.getString("accepted"));
                            value.setHotel_room(object.getString("hotel_room_no"));
                            value.setPay_status(object.getString("payment_status"));
                            model.add(value);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new bookings_adapter(getContext(), model,bookings.this));
//                        loading.dismiss();
                    } else {
//                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //loading.dismiss();
                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_LONG).show();
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

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonRequest);

    }

    private void getData(String id) {

        final android.app.AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Wait...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_bookings + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            bookings_model value = new bookings_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            value.setBookingid(object.getString("bookingid"));
                            value.setClient_id(object.getString("client_id"));
                            value.setClient_name(object.getString("client_name"));
                            value.setService_name(object.getString("service_name"));
                            value.setService_type(object.getString("service_type"));
                            value.setStart_date(object.getString("start_date"));
                            value.setStart_time(object.getString("start_time"));
                            value.setTitle_price(object.getString("title_price"));
                            value.setLocation_type(object.getString("location_type"));
                            value.setAddress(object.getString("address"));
                            value.setHours(object.getString("hours"));
                            value.setHourly_price(object.getString("hourly_price"));
                            value.setId_image(object.getString("id_image"));
                            value.setAccepted(object.getString("accepted"));
                            value.setHotel_room(object.getString("hotel_room_no"));
                            value.setPay_status(object.getString("payment_status"));
                            model.add(value);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new bookings_adapter(getContext(), model,bookings.this));
                        loading.dismiss();
                    } else {
                        loading.dismiss();
                        textView.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_LONG).show();
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
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonRequest);
    }

    @Override
    public void reload_data() {

        swipeContainer.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                // Stop animation (This will be after 3 seconds)
                getDataReload(id);
                swipeContainer.setRefreshing(false);
            }
        }, 1000);
    }
}
