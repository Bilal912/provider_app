package com.example.bus_reservation;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.bus_reservation.Activity.view_booking;
import com.example.bus_reservation.Adapter.bookings_adapter;
import com.example.bus_reservation.Adapter.chat_detail_adapter;
import com.example.bus_reservation.Model.chat_detail_model;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class chat_fragment extends Fragment {
RecyclerView recyclerView;
String id;
ArrayList<chat_detail_model> model;
TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_fragment, container, false);

        model=new ArrayList<>();

        textView=view.findViewById(R.id.no_file_id);
        recyclerView=view.findViewById(R.id.chat_recycler);

        SharedPreferences editors = getActivity().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        id = editors.getString("id","Null");

        getData(id);
        return view;
    }

    private void getData(String id) {
        final android.app.AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Please Wait...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_get_chat+id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("chats");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            chat_detail_model value=new chat_detail_model();
                            value.setId(object.getString("id"));
                            value.setClient_id(object.getString("client_id"));
                            value.setImage(object.getString("imagelocation"));
                            value.setName(object.getString("name"));
                            model.add(value);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new chat_detail_adapter(getContext(), model));
                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
                        textView.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(),"No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    loading.dismiss();
                    textView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();
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
