package com.example.carz.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carz.R;

import static com.example.carz.activities.LoginActivity.SETTINGS;
import static com.example.carz.activities.LoginActivity.settingSplash;

/**
 * Splash screen
 */
public class SplashActivity extends AppCompatActivity {

    private int getSplashScreenDuration = 3000;
    Intent intent;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        intent = new Intent(this, LoginActivity.class);

        //get setting for ignore splash
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        boolean ignoreSplash = sharedPreferences.getBoolean(settingSplash, false);

        if(ignoreSplash)
            System.out.println("IGNORE IS TRUE");
        else
            System.out.println("IGNORE IS FALSE");

        setTitle(R.string.app_name);
        setContentView(R.layout.splash_screen);

        TextView tx = findViewById(R.id.mainTitle);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/QanelasSoftDEMO-UltraLight.otf");
        tx.setTypeface(custom_font);

        ImageView img = findViewById(R.id.logo);
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        img.startAnimation(fadeIn);
        tx.startAnimation(fadeIn);

        //manages ignore splash setting
        if(!ignoreSplash)
            scheduleSplashScreen();
        else
            startActivity(intent);

    }

    /**
     * Delay to display splash
     */
    private void scheduleSplashScreen() {
        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(intent), getSplashScreenDuration);
    }
}
