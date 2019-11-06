package com.example.carz.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carz.R;

public class AccountActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.my_account);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        //add back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userKey", 0);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
