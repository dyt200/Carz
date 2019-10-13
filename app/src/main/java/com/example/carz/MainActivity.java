package com.example.carz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.carz.Objects.Car;
import com.example.carz.Objects.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void login(View view) {
        EditText emailT = findViewById(R.id.email);
        String email = emailT.getText().toString();

        EditText passT = findViewById(R.id.password);
        String pass = passT.getText().toString();

        if(checkLogin(email, pass)) {
            Intent intent = new Intent(this, CarListActivity.class);
            startActivity(intent);
        } else {
            passT.setText("");
            Toast toast =   Toast.makeText(getApplicationContext(),
                            "E-mail or password was incorrect",
                            Toast.LENGTH_SHORT
            );
            toast.setGravity(Gravity.CENTER, 0,200);
            toast.show();
        }
    }

    public void createUser(View view) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    private boolean checkLogin(String email, String pass) {
        User[] users = new User[3];
        users[0] = new User(1, "Ben", "Pocklington", "ben@test.com", "test", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");
        users[1] = new User(2, "Dylan", "Thompson", "dylan@test.com", "test","0791234567", "Route de Test 16, Ayent, Valais, Suisse");
        users[2] = new User(3, "Cloud", "Strife", "cloud@test.com", "test","0791234567", "No. 1, Sector 7, Midgar");
        users[0] = new User(1, "test", "test", "a", "a", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");

        for (User user : users) {
            if(user.getEmail().equals(email) && user.getPassword().equals(pass))
                return true;
        }
        return false;
    }
}
