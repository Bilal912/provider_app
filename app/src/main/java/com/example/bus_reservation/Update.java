package com.example.bus_reservation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Activity.Register;
import com.example.bus_reservation.Activity.second_step;
import com.example.bus_reservation.volley.CustomRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.skydoves.elasticviews.ElasticButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Update extends Fragment {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    RadioButton Male,Female,Others;
    ElasticButton update;
    TextInputEditText name,phone,username,zip_code,distnace,tag,venmo,cashapp,deposite,height,ethinicity,hair,services,instagram,twitter;
    RadioGroup radioGroup;
    EditText dob;

    String gend = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        Male=view.findViewById(R.id.male);
        Female=view.findViewById(R.id.female);
        Others=view.findViewById(R.id.others);
        phone = view.findViewById(R.id.phone);
        name=view.findViewById(R.id.name);
        username=view.findViewById(R.id.username);
        zip_code=view.findViewById(R.id.zip_code);
        dob=view.findViewById(R.id.dob);
        distnace=view.findViewById(R.id.distance);
        tag=view.findViewById(R.id.tag_line);
        venmo=view.findViewById(R.id.venmo_id);
        cashapp=view.findViewById(R.id.cash_app);
        deposite=view.findViewById(R.id.percent);
        height=view.findViewById(R.id.height);
        ethinicity=view.findViewById(R.id.ethnicity);
        hair=view.findViewById(R.id.hair);
        services=view.findViewById(R.id.service);
        instagram=view.findViewById(R.id.instagram);
        twitter=view.findViewById(R.id.twitter);

        radioGroup=view.findViewById(R.id.gender_radio);

        SharedPreferences editors = getActivity().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        final String id = editors.getString("id","Null");

        getDetail(id);

        update = view.findViewById(R.id.update);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.male:
                        gend="Male";
                        break;
                    case  R.id.female:
                        gend="Female";
                        break;
                    case R.id.others:
                        gend="Others";
                        break;
                }
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        mDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                dob.setText(date);
            }
        };

//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//          }
//     });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(name.getText().toString())){
                    makeText(getContext(), "Name is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(tag.getText().toString())){
                    makeText(getContext(), "Tag is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone.getText().toString())){
                    makeText(getContext(), "Phone Number is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(zip_code.getText().toString())){
                    makeText(getContext(), "Zip ID is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(distnace.getText().toString())){
                    makeText(getContext(), "Distance is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(username.getText().toString())){
                    makeText(getContext(), "Username is required", LENGTH_SHORT).show();
                }
                else if (radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(),"Select Gender", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(deposite.getText().toString())){
                    makeText(getContext(), "Deposite is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(dob.getText().toString())){
                    makeText(getContext(), "Date Of Birth is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(height.getText().toString())){
                    makeText(getContext(), "Height is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(ethinicity.getText().toString())){
                    makeText(getContext(), "Ethnicity is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(services.getText().toString())){
                    makeText(getContext(), "Service is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(hair.getText().toString())){
                    makeText(getContext(), "Hair Color is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(tag.getText().toString())){
                    makeText(getContext(), "Tag Line is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(venmo.getText().toString())){
                    makeText(getContext(), "Venmo ID is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(cashapp.getText().toString())){
                    makeText(getContext(), "Cash App ID is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(instagram.getText().toString())){
                    makeText(getContext(), "Instagram is required", LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(twitter.getText().toString())){
                    makeText(getContext(), "Twitter is required", LENGTH_SHORT).show();
                }
                 else {

                    getData(id,name.getText().toString().trim(), username.getText().toString().trim(), phone.getText().toString(),
                            zip_code.getText().toString(),dob.getText().toString(),distnace.getText().toString(),tag.getText().toString()
                            ,venmo.getText().toString(),cashapp.getText().toString(),deposite.getText().toString(),height.getText().toString(),
                            ethinicity.getText().toString(), hair.getText().toString(), instagram.getText().toString(),services.getText().toString(),
                            twitter.getText().toString());

                }
            }
        });

        return view;
    }

    private void getDetail(String id) {
        final AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Checking...");
        loading.show();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_Get_Data+id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Boolean status = null;
                try {
                    status = response.getBoolean("response");

                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            name.setText(object.getString("name"));
                            username.setText(object.getString("username"));
                            phone.setText(object.getString("phone"));
                            zip_code.setText(object.getString("pin_code"));
                            dob.setText(object.getString("dob"));
                            distnace.setText(object.getString("distance"));
                            tag.setText(object.getString("tagline"));
                            venmo.setText(object.getString("venmo_id"));
                            cashapp.setText(object.getString("venmo_id"));
                            deposite.setText(object.getString("deposit_percentage"));
                            height.setText(object.getString("height"));
                            ethinicity.setText(object.getString("ethnicity"));
                            hair.setText(object.getString("hair_color"));
                            services.setText(object.getString("services"));
                            instagram.setText(object.getString("instagram"));
                            twitter.setText(object.getString("twitter"));

                            String temp = object.getString("gender");
                            if (temp.equalsIgnoreCase("Male")){
                                Male.setChecked(true);
                            }
                            else if (temp.equalsIgnoreCase("Female")){
                                Female.setChecked(true);
                            }
                            else if (temp.equalsIgnoreCase("Other")){
                                Others.setChecked(true);
                            }
                        }


                        loading.dismiss();
                    } else {
                        loading.dismiss();
                        String error = response.getString("error");
                        Toast.makeText(getContext(),error, LENGTH_SHORT).show();                    }
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

    private void getData(String p_id,String name, String username, String phone, String zip, String doob, String distance,
                         String tag, String venmo, String cashapp, String deposite,
                         String height, String ethnicity, String hair,
                         String instagra, String servic, String twitty) {

        final AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Checking...");
        loading.show();

        Map<String, String> params = new Hashtable<String, String>();
        params.put("update_profile", "true");
        params.put("name", name);
        params.put("username", username);
        params.put("phone", phone);
        params.put("gender", gend);
        params.put("dob", doob);
        params.put("tagline", tag);
        params.put("hair_color", hair);
        params.put("zipcode", zip);
        params.put("distance", distance);
        params.put("venmo_id", venmo);
        params.put("cash_app_id", cashapp);
        params.put("deposit_percentage", deposite);
        params.put("height", height);
        params.put("ethnicity", ethnicity);
        params.put("instagram", instagra);
        params.put("twitter", twitty);
        params.put("services", servic);
        params.put("provider_id", p_id);

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
                        Toast.makeText(getContext(), "Data Not Updated", LENGTH_SHORT).show();
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
