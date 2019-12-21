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

/**
 * New account creation activity
 */
public class CreateUserActivity extends AppCompatActivity {

    private UserRepository ur;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        ur = UserRepository.getInstance();

        //get back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Creates a new user
     * @param view current view
     */
    public void createUser(View view) {
        //get all text from all the fields

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

        //make sure no fields are empty
        if(     email.equals("")
                || firstName.equals("")
                || lastName.equals("")
                || pass1.equals("")
                || pass2.equals("")
                || address1.equals("")
                || telephone.equals("")
        ) createToast("Please complete all of the fields!");
        else {

            //check if both password fields are the same
            if (pass1.equals(pass2)) {

                //check to see if we find a User with the same email
                ur.doesEmailExist(email, view.getContext()).observe(this, doesEmailExist -> {
                    //TODO This always triggers both on success... Fix plz
                    if(doesEmailExist == null) {
                        User user = new User("REMOVE" ,firstName, lastName, email, pass1, telephone, address1);
                        insertUser(user, view);
                    } else
                        createToast("An account with that email already exists!");
                });
            } else
                createToast("Your passwords do not match!");
        }
    }

    /**
     * Creates a short toast
     * @param text toast message
     */
    private void createToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT
        );
        toast.show();
    }

    /**
     * Insert a user
     * @param user user to insert
     * @param view current view
     */
    private void insertUser(User user, View view) {
        ur.insert(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        }, view.getContext());
    }

    /**
     * Responds to user insertion
     * @param response response boolean
     */
    private void setResponse(Boolean response) {
        if (response) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            createToast("Account created successfully!");
        } else
            createToast("Error while creating user...");
    }

}
