package com.example.carz.activities;

import android.os.Bundle;
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
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
