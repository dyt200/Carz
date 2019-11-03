package com.example.carz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.carz.Entities.CarList;
import com.example.carz.Entities.User;
import com.example.carz.R;
import com.example.carz.repositories.UserRepository;
import com.example.carz.viewmodel.UserListViewModel;
import com.example.carz.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    String e_email = "";
    String e_pass = "";
    UserRepository ur;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        setContentView(R.layout.login);
        ur = UserRepository.getInstance();

    }

    public void tryLogin(View view) {
        EditText emailT = findViewById(R.id.email);
        e_email = emailT.getText().toString();

        EditText passT = findViewById(R.id.password);
        e_pass = passT.getText().toString();

        ur.validateLogin(e_email,e_pass, view.getContext()).observe(this, userData -> {
            if(userData != null)
                login();
            else
                invalidLogin();
        });
    }

    public void createUser(View view) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    private void login(){
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
}

