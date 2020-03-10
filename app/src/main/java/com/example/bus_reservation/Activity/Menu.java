package com.example.bus_reservation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_reservation.Dashboard;
import com.example.bus_reservation.R;
import com.example.bus_reservation.Update;
import com.example.bus_reservation.bookings;
import com.example.bus_reservation.member_ship;
import com.example.bus_reservation.private_gallery;
import com.example.bus_reservation.public_gallery;
import com.example.bus_reservation.services;
import com.example.bus_reservation.update_password;
import com.google.android.material.navigation.NavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Menu extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    TextView textView,toptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toptext=findViewById(R.id.top_text);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        textView=findViewById(R.id.menu_btn);
        drawer = findViewById(R.id.drawer_layout);
        SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        String Email = editors.getString("email","Null");
        String First = editors.getString("Name","Null");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView= findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        final TextView Name=view.findViewById(R.id.nav_name);
        final TextView Nav_email=view.findViewById(R.id.nav_email);

        Nav_email.setText(Email);
        Name.setText(First);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch(id)
                {

                    case R.id.nav_dash:
                        toptext.setText(R.string.app_name);
                        Fragment newFragment3 = new Dashboard();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.nav_fragment, newFragment3);
                        transaction3.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        toptext.setText("Edit Profile");
                        Fragment newFragment2 = new Update();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.nav_fragment, newFragment2);
                        transaction2.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_membership:
                        toptext.setText("Membership");
                        Fragment newFragment4 = new member_ship();
                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.nav_fragment, newFragment4);
                        transaction4.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_service:
                        toptext.setText("Services");
                        Fragment newFragment8 = new services();
                        FragmentTransaction transaction8 = getSupportFragmentManager().beginTransaction();
                        transaction8.replace(R.id.nav_fragment, newFragment8,"SERVICE_TAG");
                        transaction8.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_bookings:
                        toptext.setText("Bookings");
                        Fragment newFragment5 = new bookings();
                        FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                        transaction5.replace(R.id.nav_fragment, newFragment5);
                        transaction5.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_public:
                        toptext.setText("Public Gallery");
                        Fragment newFragment6 = new public_gallery();
                        FragmentTransaction transaction6 = getSupportFragmentManager().beginTransaction();
                        transaction6.replace(R.id.nav_fragment, newFragment6);
                        transaction6.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_private:
                        toptext.setText("Private Gallery");
                        Fragment newFragment7 = new private_gallery();
                        FragmentTransaction transaction7 = getSupportFragmentManager().beginTransaction();
                        transaction7.replace(R.id.nav_fragment, newFragment7);
                        transaction7.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_change_password:
                        toptext.setText("Change Password");
                        Fragment newFragment = new update_password();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_fragment, newFragment);
                        transaction.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_share:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Hey check out our app at: https://play.google.com/store/apps/details?id="+getPackageName());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_logout:

                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Menu.this,SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Logout");
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.setContentText("Are you Sure?");
                        sweetAlertDialog.setConfirmText("Yes");
                        sweetAlertDialog.setCancelText("No");
                        sweetAlertDialog.show();
                        drawer.closeDrawers();

                        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        });

                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Toasty.info(Menu.this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
                                preferences.edit().clear().commit();
                                Intent intent = new Intent(Menu.this,Login.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        break;

                        default:
                        return true;
                }
                return true;
            }
        });

    }
    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (toptext.getText().toString().equals("Provider App")) {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.alert)
                        .setTitle("Exit")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            else {
                toptext.setText(R.string.app_name);
                Fragment newFragment = new Dashboard();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_fragment, newFragment);
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        }
    }
}