package com.example.bus_reservation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Activity.Forget_Password;
import com.example.bus_reservation.volley.CustomRequest;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;


public class update_password extends Fragment {
ElasticButton elasticButton;
EditText Old,New;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_password, container, false);

        elasticButton=view.findViewById(R.id.change_password);
        Old=view.findViewById(R.id.o_password);
        New=view.findViewById(R.id.new_password);
        SharedPreferences editors = getActivity().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        final String id = editors.getString("id","Null");

        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(Old.getText().toString().trim())) {
                    Toast.makeText(getContext(), "Please Enter Old Password", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(New.getText().toString().trim())){
                    Toast.makeText(getContext(), "Enter your new Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Old.getText().toString().trim().equals(New.getText().toString().trim())){
                        Toast.makeText(getContext(), "Your old and new Password are same", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        change(id,Old.getText().toString().trim(),New.getText().toString().trim());
                    }
                }
            }
        });

    return view;
    }
    private void change(String id, String old, String new_pasword) {
        final AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Checking...");
        loading.show();

        Map<String, String> params = new Hashtable<String, String>();
        params.put("change_password", "true");
        params.put("provider_id", id);
        params.put("old_password",old);
        params.put("password",new_pasword);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");

                    if (status) {
                        loading.dismiss();
                        Toast.makeText(getContext(), response.getString("data"), LENGTH_SHORT).show();
                        Fragment fragment = new Dashboard();
                        FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.nav_fragment, fragment);
                        transaction2.commit();
                    } else {
                        loading.dismiss();
                        Toast.makeText(getContext(), "Password Not Updated", LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    loading.dismiss();
                    makeText(getActivity(), "Something Went Wrong", LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(getContext(), "Something went wrong" + error, LENGTH_LONG).show();
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
