package com.example.carz.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carz.R;

import static com.example.carz.activities.MainActivity.SETTINGS;
import static com.example.carz.activities.MainActivity.settingShowMyCars;
import static com.example.carz.activities.MainActivity.settingSplash;

/**
 * Settings page
 */
public class AppSettingsActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get shared preferences from settings
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean ignoreSplash = sharedPreferences.getBoolean(settingSplash, false);
        boolean showMyCars = sharedPreferences.getBoolean(settingShowMyCars, false);

        //set view
        setTitle(R.string.appSettings);
        setContentView(R.layout.app_settings);

        //add back buttons
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get toggles
        ToggleButton ignoreSplashToggle = findViewById(R.id.splash_toggle);
        ToggleButton showMyCarsToggle = findViewById(R.id.show_my_cars_toggle);

        //set toggle values
        if(ignoreSplash)
            ignoreSplashToggle.setChecked(true);

        if(showMyCars)
            showMyCarsToggle.setChecked(true);

        //listeners to change sharedPreferences values
        ignoreSplashToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                editor.putBoolean(settingSplash, true);
            else
                editor.putBoolean(settingSplash, false);

            editor.apply();
        });
        showMyCarsToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                editor.putBoolean(settingShowMyCars, true);
            else
                editor.putBoolean(settingShowMyCars, false);

            editor.apply();
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
