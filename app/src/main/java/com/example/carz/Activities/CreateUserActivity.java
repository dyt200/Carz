package com.example.carz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carz.Database.Entities.User;
import com.example.carz.R;
import com.example.carz.Database.Repository.UserRepo;
import com.example.carz.Util.OnAsyncEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * New account creation activity
 */
public class CreateUserActivity extends AppCompatActivity {

    private UserRepo ur;
    private static final String TAG = "CreateUserActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        ur = UserRepo.getInstance();

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

        if (isEmailValid(email) && isPassLongEnough(pass1)){
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

                    User user = new User(firstName, lastName, email, pass1, telephone, address1);

                    ur.register(user, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "createUserWithEmail: success");
                            setResponse(true);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "createUserWithEmail: failure", e);
                            setResponse(false);
                        }
                    });

                } else
                    createToast("Your passwords do not match!");
            }
        } else {
            createToast("Invalid Email address or password too short !");
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

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * @param pass
     * @return boolean true for valid false for invalid
     */
    public static boolean isPassLongEnough(String pass) {
        if(pass.length() >= 6) return true;
        else return false;

    }

}
