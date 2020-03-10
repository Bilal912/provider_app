package com.example.bus_reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Activity.add_sprovid;
import com.example.bus_reservation.Adapter.bookings_adapter;
import com.example.bus_reservation.Adapter.service_adapter;
import com.example.bus_reservation.Model.bookings_model;
import com.example.bus_reservation.Model.service_model;
import com.example.bus_reservation.R;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class services extends Fragment implements reload_data{
RecyclerView recyclerView;
TextView textView;
ArrayList<service_model> model;
ElasticButton elasticButton;
    String id;
    service_adapter service_adap;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);


        recyclerView=view.findViewById(R.id.recylcer_id);
        textView=view.findViewById(R.id.no_file_id);

        SharedPreferences editors = getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        id = editors.getString("id","Null");

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
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


        elasticButton=view.findViewById(R.id.add_service);
        model=new ArrayList<>();

        getData(id);

        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), add_sprovid.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getDataReload(String id) {
//
//        final android.app.AlertDialog loading = new ProgressDialog(getContext());
//        loading.setMessage("Wait...");
//        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_get_services+id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    model.clear();
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            service_model value = new service_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            value.setSevice_id(object.getString("service_id"));
                            value.setIncall_rate(object.getString("incall_rate"));
                            value.setOutcall_rate(object.getString("outcall_rate"));
                            value.setTitle(object.getString("title"));
                            value.setDescription(object.getString("description"));
                            value.setService_name(object.getString("category_name"));
                            value.setCat_name(object.getString("category_name"));
                            model.add(value);
                        }
//                            service_adap.notifyDataSetChanged();
                            service_adap= new service_adapter(getContext(), model,services.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(service_adap);

//                        loading.dismiss();
                    }
                    else {
//                        loading.dismiss();
//                        Toast.makeText(getActivity(),"No Data Found", Toast.LENGTH_SHORT).show();
//                        textView.setVisibility(View.VISIBLE);
                    }

                }
                catch (Exception e){
                    //loading.dismiss();
                    Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
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
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_get_services+id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            service_model value = new service_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            value.setSevice_id(object.getString("service_id"));
                            value.setIncall_rate(object.getString("incall_rate"));
                            value.setOutcall_rate(object.getString("outcall_rate"));
                            value.setTitle(object.getString("title"));
                            value.setDescription(object.getString("description"));
                            value.setService_name(object.getString("category_name"));
                            value.setCat_name(object.getString("category_name"));
                            model.add(value);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new service_adapter(getContext(),model,services.this));
                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(getActivity(),"No Data Found", Toast.LENGTH_SHORT).show();
                        textView.setVisibility(View.VISIBLE);
                    }

                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
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
