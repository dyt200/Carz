package com.example.carz.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carz.R;

import static com.example.carz.activities.MainActivity.SETTINGS;
import static com.example.carz.activities.MainActivity.settingSplash;

/**
 * Settings page
 */
public class AppSettingsActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get shared preferences from settings
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        boolean ignoreSplash = sharedPreferences.getBoolean(settingSplash, false);

        //set view
        setTitle(R.string.appSettings);
        setContentView(R.layout.app_settings);

        //add back buttons
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get toggles
        ToggleButton ignoreSplashToggle = findViewById(R.id.splash_toggle);

        //set toggle values
        if(ignoreSplash)
            ignoreSplashToggle.setChecked(true);
        else
            ignoreSplashToggle.setChecked(false);

        //listeners
        ignoreSplashToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (isChecked)
                editor.putBoolean(settingSplash, true);
            else
                editor.putBoolean(settingSplash, false);

            editor.apply();
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
