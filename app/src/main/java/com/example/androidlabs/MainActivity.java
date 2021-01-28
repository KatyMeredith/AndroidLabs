package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);

        Button clickButton = (Button) findViewById(R.id.clickButton);
        CheckBox clickBox = (CheckBox) findViewById(R.id.checkThisOut);
        Switch switchBox = (Switch) findViewById(R.id.switchOnOff);

        clickButton.setOnClickListener(click -> {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toast_message) ;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });


        clickBox.setOnCheckedChangeListener((checkbox, bool) -> {
            Snackbar
                    .make(clickBox, getString(R.string.undo), Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbarText, click -> {
                        checkbox.setChecked(!bool);
                    })
                    .show();
        });

        switchBox.setOnCheckedChangeListener((checkbox, bool) -> {
            Snackbar
                    .make(clickBox, getString(R.string.undo), Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbarText, click -> {
                        checkbox.setChecked(!bool);
                    })
                    .show();
        });
    }
}