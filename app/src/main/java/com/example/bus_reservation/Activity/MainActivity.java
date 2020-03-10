package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.bus_reservation.R;

import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class MainActivity extends AppCompatActivity {

    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = new Thread() {
            public void run() {
                try {

                    t.sleep(1500);

                    SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
                    String Email = editors.getString("email","Null");

                    if (Email.equals("Null")) {
                        Intent i = new Intent(MainActivity.this, Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent i = new Intent(MainActivity.this, Menu.class);
                        startActivity(i);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}
