package com.example.bus_reservation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Activity.view_service;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.Model.bookings_model;
import com.example.bus_reservation.Model.service_model;
import com.example.bus_reservation.R;
import com.example.bus_reservation.reload_data;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class service_adapter extends RecyclerView.Adapter<service_adapter.GithubViewHolder> {
    private Context context;
    private ArrayList<service_model> data;
    private reload_data reload_data;
    public service_adapter(Context context, ArrayList<service_model> data,reload_data reload_data) {
        this.context=context;
        this.data=data;
        this.reload_data=reload_data;
    }

    @NonNull
    @Override
    public service_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_cardview,parent,false);
        return new service_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder holder, final int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.outcall.setText("Out Call Rate :  $".concat(data.get(position).getOutcall_rate()));
        holder.incall.setText("In Call Rate :  $".concat(data.get(position).getIncall_rate()));
        holder.name.setText("Category :  ".concat(data.get(position).getService_name()));

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete_service(data.get(position).getSevice_id());

            }
        });
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, view_service.class);
                intent.putExtra("title",data.get(position).getTitle());
                intent.putExtra("description",data.get(position).getDescription());
                intent.putExtra("service_id",data.get(position).getSevice_id());
                intent.putExtra("service_name",data.get(position).getService_name());
                intent.putExtra("incall",data.get(position).getIncall_rate());
                intent.putExtra("outcall",data.get(position).getOutcall_rate());
                intent.putExtra("cat_name",data.get(position).getCat_name());
                intent.putExtra("status","edit");
                context.startActivity(intent);
            }
        });
        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, view_service.class);
                intent.putExtra("title",data.get(position).getTitle());
                intent.putExtra("description",data.get(position).getDescription());
                intent.putExtra("service_id",data.get(position).getSevice_id());
                intent.putExtra("service_name",data.get(position).getService_name());
                intent.putExtra("incall",data.get(position).getIncall_rate());
                intent.putExtra("outcall",data.get(position).getOutcall_rate());
                intent.putExtra("cat_name",data.get(position).getCat_name());
                intent.putExtra("status","view");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder {
        ElasticButton View,Edit,Delete;
        TextView title,name,incall,outcall;
        public GithubViewHolder(View view) {
            super(view);

            View=view.findViewById(R.id.view_id);
            Edit=view.findViewById(R.id.edit);
            Delete=view.findViewById(R.id.delete);

            title=view.findViewById(R.id.title);
            name=view.findViewById(R.id.cate_name);
            incall=view.findViewById(R.id.incall);
            outcall=view.findViewById(R.id.outcall);

        }
    }

    private void Edit_service() {

    }

    private void Delete_service(final String sevice_id) {
        final SweetAlertDialog pdial = new SweetAlertDialog(context,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pdial.setCustomImage(R.drawable.ic_delete_black_24dp);
        pdial.setContentText("Are you sure?");
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
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_delete_service+sevice_id, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Boolean status = null;
                        try {
                            status = response.getBoolean("response");
                            if (status) {
                                loading.dismiss();
                                reload_data.reload_data();
                            }
                            else {
                                loading.dismiss();
                                Toast.makeText(context,"Data not deleted", Toast.LENGTH_SHORT).show();
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

}