package com.example.carz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.carz.Entities.User;
import com.example.carz.R;
import com.example.carz.repositories.UserRepository;
import com.example.carz.util.OnAsyncEventListener;


public class CreateUserActivity extends AppCompatActivity {

    private UserRepository ur;
    private Toast toast;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        ur = UserRepository.getInstance();
    }

    public void create_user(View view) {

        EditText emailT = findViewById(R.id.email);
        String email = emailT.getText().toString();

        EditText firstNameT = findViewById(R.id.firstName);
        String firstName = firstNameT.getText().toString();

        EditText lastNameT = findViewById(R.id.lastName);
        String lastName = lastNameT.getText().toString();

        EditText pass1T = findViewById(R.id.pass1);
        String pass1 = pass1T.getText().toString();

        EditText pass2T = findViewById(R.id.pass2);
        String pass2 = pass2T.getText().toString();

        EditText address1T = findViewById(R.id.address1);
        String address1 = address1T.getText().toString();


        EditText telephoneT = findViewById(R.id.telephone);
        String telephone = telephoneT.getText().toString();

        if (pass1.equals(pass2)){
            User user = new User(firstName, lastName, email, pass1, telephone, address1);
            ur.insert(user, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    setResponse(true);
                }

                @Override
                public void onFailure(Exception e) {
                    setResponse(false);
                }
            }, view.getContext() );
        } else {
            toast = Toast.makeText(getApplicationContext(),
                    "Password verification incorrect!",
                    Toast.LENGTH_SHORT
            );
            toast.show();
        }


    }

    private void setResponse(Boolean response) {
        if (response) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            toast = Toast.makeText(getApplicationContext(),
                    "Account created successfully !",
                    Toast.LENGTH_SHORT
            );
        } else {
            toast = Toast.makeText(getApplicationContext(),
                    "Creation errror",
                    Toast.LENGTH_SHORT
            );
        }
        toast.show();
    }

}
