package com.example.carz.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;


import com.example.carz.R;
import com.example.carz.Database.Repository.UserRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    String e_email = "";
    String e_pass = "";
    UserRepo userRepo;

    public static final String MyPREFERENCES = "Session" ;
    public static final String SETTINGS = "settings" ;
    public static final String settingSplash = "splash";
    public static final String settingShowMyCars = "showMyCars";
    public static final String UserId = "userKey";

    SharedPreferences sharedpreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        setContentView(R.layout.login);
        userRepo = UserRepo.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // TODO Display dont diplay login page if user is allready connected
        if (user != null){
            login(user);
        }
    }

    /**
     * Make a login attempt
     * @param view current view
     */
    public void tryLogin(View view) {
        EditText emailT = findViewById(R.id.email);
        e_email = emailT.getText().toString();

        EditText passT = findViewById(R.id.password);
        e_pass = passT.getText().toString();

        if (!e_email.equals("") && !e_pass.equals("")){
            userRepo.signIn(e_email, e_pass, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    login(user);
                } else {
                    invalidLogin();
                }
            });
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please fill in all the fields",
                    Toast.LENGTH_SHORT
            );
            toast.setGravity(Gravity.CENTER, 0, 200);
            toast.show();
        }
    }
    /**
     * Navigate to CreateUser Activity
     * @param view curr view
     */
    public void createUser(View view) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    /**
     * Navigate to app and store session
     * @param firebaseUser current user
     */
    private void login(FirebaseUser firebaseUser){
        userRepo.getUser(firebaseUser.getUid()).observe(this, userData -> {
            if(userData != null){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(UserId, userData.getId());
                editor.apply();
                Intent intent = new Intent(this, CarListActivity.class);
                intent.putExtra("action", "all_cars");
                startActivity(intent);
            }
            else
                invalidLogin();
        });

    }

    /**
     * Invaid login procedure
     */
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

