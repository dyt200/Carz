package com.example.carz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carz.Entities.CarList;
import com.example.carz.Entities.User;
import com.example.carz.R;

public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        setContentView(R.layout.login);
    }

            public void login(View view) {
                EditText emailT = findViewById(R.id.email);
                String email = emailT.getText().toString();

                EditText passT = findViewById(R.id.password);
                String pass = passT.getText().toString();

                if(checkLogin(email, pass)) {
                    CarList cars = new CarList();
                    Intent intent = new Intent(this, CarListActivity.class);
                    intent.putExtra("carList", cars.getList());
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
        users[0] = new User("Ben", "Pocklington", "ben@test.com", "test", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");
        users[1] = new User("Dylan", "Thompson", "dylan@test.com", "test","0791234567", "Route de Test 16, Ayent, Valais, Suisse");
        users[2] = new User("Cloud", "Strife", "cloud@test.com", "test","0791234567", "No. 1, Sector 7, Midgar");
        users[0] = new User("test", "test", "a", "a", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");

        for (User user : users) {
            if(user.getEmail().equals(email) && user.getPassword().equals(pass))
                return true;
        }
        return false;
    }
}
