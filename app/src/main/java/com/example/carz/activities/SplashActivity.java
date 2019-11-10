package com.example.carz.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carz.R;

public class SplashActivity extends AppCompatActivity {

    private int getSplashScreenDuration = 3000;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);
        setContentView(R.layout.splash_screen);

        TextView tx = findViewById(R.id.mainTitle);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/QanelasSoftDEMO-UltraLight.otf");
        tx.setTypeface(custom_font);

        ImageView img = findViewById(R.id.logo);
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        img.startAnimation(fadeIn);
        tx.startAnimation(fadeIn);

        scheduleSplashScreen();

    }

    private void scheduleSplashScreen() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }, getSplashScreenDuration);
    }

    @Override
    public void onBackPressed() {

    }
}
