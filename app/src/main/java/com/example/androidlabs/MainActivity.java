package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);

        EditText emailField = (EditText)findViewById(R.id.email);

        SharedPreferences prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String savedEmail = prefs.getString("email", "");

        emailField.setText(savedEmail);
    }

    @Override
    protected void onPause() {
        super.onPause();

        String email = ((EditText)findViewById(R.id.email)).getText().toString();

        SharedPreferences prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString("email", email);
        edit.commit();
    }


}