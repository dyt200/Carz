package com.example.carz.activities;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carz.R;

public class AppSettingsActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.appSettings);
        setContentView(R.layout.app_settings);

        //add back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ToggleButton darkModeT = findViewById(R.id.dark_mode);
        darkModeT.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setTheme(R.style.CarzDark);
                setContentView(R.layout.app_settings);
            } else {
                setTheme(R.style.CarzLight);
                setContentView(R.layout.app_settings);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
