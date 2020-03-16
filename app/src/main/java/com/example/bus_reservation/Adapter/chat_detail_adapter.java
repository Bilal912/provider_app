package com.example.bus_reservation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.bus_reservation.Activity.Show_image;
import com.example.bus_reservation.Activity.chat_screen;
import com.example.bus_reservation.Activity.view_booking;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.Model.bookings_model;
import com.example.bus_reservation.Model.chat_detail_model;
import com.example.bus_reservation.R;
import com.example.bus_reservation.reload_data;
import com.skydoves.elasticviews.ElasticButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class chat_detail_adapter extends RecyclerView.Adapter<chat_detail_adapter.GithubViewHolder> {
    private Context context;
    private ArrayList<chat_detail_model> data;
    private reload_data reload;
    public chat_detail_adapter(Context context, ArrayList<chat_detail_model> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public chat_detail_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_detail_cardview,parent,false);
        return new chat_detail_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder holder, final int position) {

        holder.textView.setText(data.get(position).getName());

        final String image = "http://marketist.eparking.website/Client/".concat(data.get(position).getImage());
        Picasso.with(context).load(image).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, Show_image.class);
                intent.putExtra("image",image);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(context, chat_screen.class);
                i2.putExtra("name",data.get(position).getName());
                i2.putExtra("id",data.get(position).getId());
                i2.putExtra("image",image);
                i2.putExtra("client_id",data.get(position).getClient_id());
                context.startActivity(i2);
            }
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class GithubViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public GithubViewHolder(View view) {
            super(view);

            imageView=view.findViewById(R.id.image_mem);
            textView=view.findViewById(R.id.mem_name);

        }
    }
}
