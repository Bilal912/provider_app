package com.example.bus_reservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Adapter.membership_adapter;
import com.example.bus_reservation.Model.membership_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class member_ship extends Fragment {
RecyclerView recyclerView;
ArrayList<membership_model> model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_ship, container, false);

        model=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recycler_id);

        getData();

        return view;
    }

    private void getData() {
        final android.app.AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Wait...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_membership, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    JSONArray jsonArray = response.getJSONArray("data");
                    if (status) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            membership_model value = new membership_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                                value.setMembership_id(object.getString("mambershipid"));
                                value.setName(object.getString("name"));
                                value.setBuy(object.getString("buy"));
                                value.setPrice(object.getString("price"));
                                value.setSee(object.getString("see"));
                                model.add(value);
                        }

                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new membership_adapter(getContext(),model));
                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(getActivity(),"No Data Found", Toast.LENGTH_SHORT).show();
                    }


                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(getActivity(),"No Data Found", Toast.LENGTH_SHORT).show();
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

}
