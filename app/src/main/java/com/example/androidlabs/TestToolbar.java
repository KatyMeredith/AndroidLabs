package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String toastText;

        switch(item.getItemId()) {
            case R.id.firstMenuButton:
                toastText = getString(R.string.first_button_toast);
                break;

            case R.id.secondMenuButton:
                toastText = getString(R.string.second_button_toast);
                break;

            case R.id.thirdMenuButton:
                toastText = getString(R.string.third_button_toast);
                break;
            case R.id.fourthMenuButton:
                toastText = getString(R.string.overflow_toast);
                break;

            default:
                toastText = getString(R.string.no);
        }

            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_to_chat:
                startActivity(new Intent(this, ChatRoomActivity.class));
                break;
            case R.id.nav_to_weather:
                startActivity(new Intent(this, WeatherForecast.class));
                break;
            case R.id.nav_to_login:
                setResult(500);
                finish();
                break;
        }


        return true;
    }
}