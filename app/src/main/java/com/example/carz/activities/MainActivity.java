package com.example.carz.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import com.example.carz.Entities.User;
import com.example.carz.R;
import com.example.carz.repositories.UserRepository;

public class MainActivity extends AppCompatActivity {

    String e_email = "";
    String e_pass = "";
    UserRepository ur;

    public static final String MyPREFERENCES = "Session" ;
    public static final String UserId = "userKey";
    SharedPreferences sharedpreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        setContentView(R.layout.login);
        ur = UserRepository.getInstance();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void tryLogin(View view) {
        EditText emailT = findViewById(R.id.email);
        e_email = emailT.getText().toString();

        EditText passT = findViewById(R.id.password);
        e_pass = passT.getText().toString();

        ur.validateLogin(e_email,e_pass, view.getContext()).observe(this, userData -> {
            if(userData != null)
                login(userData);
            else
                invalidLogin();
        });
    }

    public void createUser(View view) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    private void login(User userData){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(UserId, userData.getId());
        editor.apply();
        Intent intent = new Intent(this, CarListActivity.class);
        intent.putExtra("action", "all_cars");
        startActivity(intent);
    }

    private void invalidLogin() {
        EditText passT = findViewById(R.id.password);
        passT.setText("");
        Toast toast = Toast.makeText(getApplicationContext(),
                "E-mail or password was incorrect",
                Toast.LENGTH_SHORT
        );
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        int userId = sharedpreferences.getInt("userKey", 0);

        if (userId == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You must be logged in to go back!",
                    Toast.LENGTH_SHORT
            );
            toast.setGravity(Gravity.CENTER, 0, 200);
            toast.show();
        } else {
            super.onBackPressed();
        }
    }
}

