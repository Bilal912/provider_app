package com.example.bus_reservation.Adapter;

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

import com.example.bus_reservation.Activity.Payumoney;
import com.example.bus_reservation.Model.membership_model;
import com.example.bus_reservation.R;
import com.skydoves.elasticviews.ElasticButton;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class membership_adapter extends RecyclerView.Adapter<membership_adapter.GithubViewHolder> {
    private Context context;
    private ArrayList<membership_model> data;

    public membership_adapter(Context context, ArrayList<membership_model> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public membership_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.membership_cardview,parent,false);
        return new membership_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder holder, final int position) {

        SharedPreferences editors = context.getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        final String id = editors.getString("Membership_id","Null");

        holder.price.setText(data.get(position).getPrice()+"$");
        holder.name.setText(data.get(position).getName());
        if (data.get(position).getBuy().equals("0")) {
            holder.buy.setText("Can Be able to Buy additional Bids :- No");
        }
        else {
            holder.buy.setText("Can Be able to Buy additional Bids :- Yes");
        }
        if (data.get(position).getSee().equals("0")){
            holder.see.setText("Can Be able to see Other Bids :- No");
        }
        else {
            holder.see.setText("Can Be able to see Other Bids :- Yes");
        }
        if (data.get(position).getMembership_id().trim().equals(id)){
            holder.elasticButton.setText("Selected");
            holder.elasticButton.setBackgroundColor(Color.parseColor("#fea984"));
        }
        else {
            holder.elasticButton.setText("Select");
            holder.elasticButton.setBackgroundColor(Color.parseColor("#ffc0cb"));
        }

        holder.elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(position).getMembership_id().trim().equals(id)){
                    //do nothing
                }
                else {
                    Intent intent = new Intent(context, Payumoney.class);
                    intent.putExtra("price",data.get(position).getPrice());
                    intent.putExtra("membership_id",data.get(position).getMembership_id());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder {
ElasticButton elasticButton;
TextView see,buy,price,name;
        public GithubViewHolder(View view) {
            super(view);

            elasticButton=view.findViewById(R.id.select);
            see=view.findViewById(R.id.membershipseebid);
            buy=view.findViewById(R.id.membershipbuybid);
            price=view.findViewById(R.id.membershipprice);
            name=view.findViewById(R.id.membershipname);
        }
    }
}
