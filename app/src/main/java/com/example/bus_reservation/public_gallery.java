package com.example.bus_reservation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Adapter.bookings_adapter;
import com.example.bus_reservation.Adapter.gallery_adapter;
import com.example.bus_reservation.Model.bookings_model;
import com.example.bus_reservation.Model.gallery_model;
import com.example.bus_reservation.volley.VolleyMultipartRequest;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class public_gallery extends Fragment implements reload_data{
RecyclerView recyclerView;
ArrayList<gallery_model> model;
ElasticButton elasticButton;
    private SwipeRefreshLayout swipeContainer;
Bitmap bitmap;
Uri uri;
TextView textView;
String id,memnership_id;
String temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_public_gallery, container, false);

        textView=view.findViewById(R.id.no_file_id);

        recyclerView=view.findViewById(R.id.recyler_id);
        elasticButton=view.findViewById(R.id.add_gallery);

        model=new ArrayList<>();

        SharedPreferences editors = getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        id = editors.getString("id","Null");
        memnership_id = editors.getString("Membership_id","Null");

        getdefaultdata(memnership_id);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                // To keep animation for 4 seconds
                getdefaultdata(memnership_id);
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

        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImage, 0);
            }
        });

        return view;
    }

    private void getDataReload(String id) {
//        final android.app.AlertDialog loading = new ProgressDialog(getContext());
//        loading.setMessage("Wait...");
//        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_public_gallery+id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    model.clear();
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            gallery_model value = new gallery_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            value.setImage(object.getString("image"));
                            value.setId(object.getString("id"));
                            value.setDate(object.getString("date_added"));
                            model.add(value);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new gallery_adapter(getContext(),model,temp,elasticButton,public_gallery.this));
//                        loading.dismiss();
                    }
                    else {
//                        loading.dismiss();
//                        Toast.makeText(getActivity(),"Empty Gallery", Toast.LENGTH_SHORT).show();
//                        textView.setVisibility(View.VISIBLE);
                    }

                }
                catch (Exception e){
//                    loading.dismiss();
                    Toast.makeText(getActivity(),"No Data Found", Toast.LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loading.dismiss();
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
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_public_gallery+id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            gallery_model value = new gallery_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            value.setImage(object.getString("image"));
                            value.setId(object.getString("id"));
                            value.setDate(object.getString("date_added"));
                            model.add(value);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new gallery_adapter(getContext(),model,temp,elasticButton,public_gallery.this));
                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
//                        Toast.makeText(getActivity(),"Empty Gallery", Toast.LENGTH_SHORT).show();
                        textView.setVisibility(View.VISIBLE);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
                uri = data.getData();
                try {
                    Bitmap bit = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    bitmap = bit;
                    uploadbitmap(bitmap,id);

                } catch (IOException e) {
                    makeText(getContext(),"Error Loading Image", LENGTH_SHORT).show();
                }
        }
    }
    private void getdefaultdata(final String check_id) {
//        final android.app.AlertDialog loading = new ProgressDialog(getContext());
//        loading.setMessage("Wait...");
//        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Default_membership, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String temp_default = object.getString("default_membership");
                            if (temp_default.equals(check_id)){
                                temp = "default";
                            }
                            else {
                                temp = "paid";
                            }
                        }
          //              loading.dismiss();
                    }
                    else {
            //            loading.dismiss();
                    }
                }
                catch (Exception e){
              //      loading.dismiss();
                    Toast.makeText(getActivity(),"No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // loading.dismiss();
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

    private void uploadbitmap(final Bitmap bitmap, final String id) {
        final AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Sending Image...");
        loading.setCancelable(false);
        loading.show();
        VolleyMultipartRequest jsonRequest = new VolleyMultipartRequest(Request.Method.POST, Constant.Base_url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Boolean status = null;
                try {
                    JSONObject obj = new JSONObject(new String(response.data));

                    status = obj.getBoolean("response");
                    String msg = obj.getString("data");
                    if (status) {
                        //Toast.makeText(getActivity(),"Image Added , Please Refreash !", Toast.LENGTH_SHORT).show();
                        reload_data();
                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    loading.dismiss();
                    makeText(getContext(),"Connection Error", LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(getContext(), "Something went wrong" + error, LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("provider_id",id);
                params.put("add_publicImage","true");
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long ima = System.currentTimeMillis();
                String imagename2 = String.valueOf(ima).concat(".jpg");
                params.put("photoimg",  new VolleyMultipartRequest.DataPart(imagename2, getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

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
        Volley.newRequestQueue(getContext()).add(jsonRequest);
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void reload_data() {
        swipeContainer.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                // Stop animation (This will be after 3 seconds)
                getdefaultdata(memnership_id);
                getDataReload(id);
                swipeContainer.setRefreshing(false);
            }
        }, 1000);
    }
}
