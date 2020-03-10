package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.R;
import com.example.bus_reservation.volley.CustomRequest;
import com.skydoves.elasticviews.ElasticButton;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class Register extends AppCompatActivity {
  TextView back;
  ElasticButton Register;
  EditText name,tag,email,username,phone,password,c_password,zip,distance;
  Spinner spinner;
  RadioGroup radioGroup;
  String gender = null;
  LinearLayout countrypicker;
  TextView phoneCode;
  ImageView flg;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    countrypicker=findViewById(R.id.countrypicker);
    phoneCode=findViewById(R.id.phone_code);
    flg=findViewById(R.id.flag);

    radioGroup=findViewById(R.id.gender_radio);
    back=findViewById(R.id.back);
    Register=findViewById(R.id.next);

    name=(EditText)findViewById(R.id.name);
    email=findViewById(R.id.email);
    phone=findViewById(R.id.phone);
    zip=findViewById(R.id.zip_code);
    distance=findViewById(R.id.distance);
    password=findViewById(R.id.password);
    tag=findViewById(R.id.tag_line);
    username=findViewById(R.id.username);
    c_password=findViewById(R.id.confirm_password);

    countrypicker.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
        picker.setListener(new CountryPickerListener() {
          @Override
          public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
            phoneCode.setText(dialCode);
            flg.setImageResource(flagDrawableResID);
            picker.dismiss();
          }
        });
        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
      }
    });

    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
          case R.id.male:
            gender="Male";
            break;
          case  R.id.female:
            gender="Female";
            break;
          case R.id.others:
            gender="Others";
            break;
        }
      }
    });

    back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    Register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if (TextUtils.isEmpty(name.getText().toString())){
          makeText(Register.this, "Name is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tag.getText().toString())){
          makeText(Register.this, "Tag is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email.getText().toString())){
          makeText(Register.this, "Email is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone.getText().toString())){
          makeText(Register.this, "Phone Number is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password.getText().toString())){
          makeText(Register.this, "Password is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(c_password.getText().toString())){
          makeText(Register.this, "Confirm Password is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(zip.getText().toString())){
          makeText(Register.this, "Zip ID is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(distance.getText().toString())){
          makeText(Register.this, "Distance is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(username.getText().toString())){
          makeText(Register.this, "Username is required", LENGTH_SHORT).show();
        }
        else if (radioGroup.getCheckedRadioButtonId() == -1){
          Toast.makeText(Register.this,"Select Gender", LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
          email.setError("Email is not Valid");
          email.requestFocus();
        }
        else {

          if (password.getText().toString().equals(c_password.getText().toString())) {
            String country = phoneCode.getText().toString();

            Intent intent = new Intent(Register.this, second_step.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("tag", tag.getText().toString());
            intent.putExtra("email", email.getText().toString());
            intent.putExtra("username", username.getText().toString());
            intent.putExtra("gender", gender);
            intent.putExtra("phone", country.concat(phone.getText().toString()));
            intent.putExtra("zip", zip.getText().toString());
            intent.putExtra("password", password.getText().toString());
            intent.putExtra("distance", distance.getText().toString());
            startActivity(intent);

          }
          else {
            Toast.makeText(Register.this,"Password Not Match", LENGTH_SHORT).show();
          }
        }
      }
    });

  }
}
