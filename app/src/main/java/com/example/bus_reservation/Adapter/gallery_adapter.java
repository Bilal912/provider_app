package com.example.bus_reservation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.example.bus_reservation.Activity.Show_image;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.Model.gallery_model;
import com.example.bus_reservation.Model.membership_model;
import com.example.bus_reservation.R;
import com.example.bus_reservation.reload_data;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class gallery_adapter extends RecyclerView.Adapter<gallery_adapter.GithubViewHolder> {
    private Context context;
    private ArrayList<gallery_model> data;
    private String check;
    private ElasticButton elasticButton;
    private reload_data reload;
    public gallery_adapter(Context context, ArrayList<gallery_model> data, String check, ElasticButton elasticButton,reload_data reload) {
        this.context=context;
        this.data=data;
        this.check=check;
        this.reload=reload;
        this.elasticButton=elasticButton;
    }

    @NonNull
    @Override
    public gallery_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.gallery_cardview,parent,false);
        return new gallery_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder holder, final int position) {

        holder.textView.setText(data.get(position).getDate());
        final String img = Constant.gallery_url.concat(data.get(position).getImage());

        Picasso.with(context).load(img)
                .resize(600, 600)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError() {
//                        Toast.makeText(Show_image.this,"Error in Loading Picture",Toast.LENGTH_LONG).show();
                    }
                });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Show_image.class);
                intent.putExtra("image",Constant.gallery_url.concat(data.get(position).getImage()));
                context.startActivity(intent);
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
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
                        Delete_image(data.get(position).getId());
                    }
                });
                pdial.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pdial.dismiss();
                    }
                });
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (check.equals("default")){
            if (data.size() >= 3){
                elasticButton.setVisibility(View.GONE);
            }
            else {
                elasticButton.setVisibility(View.VISIBLE);
            }
        }
        else {
            if (data.size() >= 5){
                elasticButton.setVisibility(View.GONE);
            }
            else {
                elasticButton.setVisibility(View.VISIBLE);
            }
        }
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder {
        ElasticImageView imageView;
        TextView textView;
        ProgressBar progressBar;
        public GithubViewHolder(View view) {
            super(view);
            progressBar=view.findViewById(R.id.pg);
            imageView=view.findViewById(R.id.image);
            textView=view.findViewById(R.id.date);
        }
    }
    private void Delete_image(String temp) {
        final android.app.AlertDialog loading = new ProgressDialog(context);
        loading.setMessage("Wait...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_delete_gallery+temp, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    if (status) {
                        //Toast.makeText(context,response.getString("data"),Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        reload.reload_data();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(context,"Image not deleted", Toast.LENGTH_SHORT).show();
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
}
