package com.example.carz.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.carz.Entities.User;
import com.example.carz.R;
import com.example.carz.util.OnAsyncEventListener;
import com.example.carz.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Account details activity
 */
public class AccountActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    UserViewModel userViewModel;
    boolean displayModeActive = true;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.my_account);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        //add back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listener for My Cars button
        Button myCarsBtn = findViewById(R.id.my_cars_btn);
        myCarsBtn.setOnClickListener(view -> {
            Intent myCarsIntent = new Intent(view.getContext(), CarListActivity.class);
            myCarsIntent.putExtra("action", "my_cars");
            startActivity(myCarsIntent);
        });

        //listener for Logout button (destroys session)
        Button logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent logoutIntent = new Intent(view.getContext(), MainActivity.class);
            startActivity(logoutIntent);
            createToast("Logged-out successfully");
        });

        //get user session
        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        final int userId = sharedPreferences.getInt("userKey", 0);

        //builds current user from sharedPreferences and displays it
        UserViewModel.UserFromIdFactory userFactory = new UserViewModel.UserFromIdFactory(getApplication(), userId);
        userViewModel = ViewModelProviders.of(this, userFactory).get(UserViewModel.class);
        userViewModel.getUser().observe(this, userData -> {
            if(userData != null) {
                user = userData;
                displayMode();
            }
        });
    }

    /**
     * Enters display mode
     */
    private void displayMode() {
        RelativeLayout displayMode = findViewById(R.id.display_mode);
        RelativeLayout editMode = findViewById(R.id.edit_mode);

        //manages visibility of the two layouts
        editMode.setVisibility(View.GONE);
        displayMode.setVisibility(View.VISIBLE);

        TextView emailView = findViewById(R.id.email);
        emailView.setText(user.getEmail());

        TextView firstNameView = findViewById(R.id.first_name_edit);
        firstNameView.setText(user.getFirstName());

        TextView lastNameView = findViewById(R.id.last_name_edit);
        lastNameView.setText(user.getLastName());

        TextView phoneView = findViewById(R.id.phone_edit);
        phoneView.setText(user.getTelephone());

        TextView emailView2 = findViewById(R.id.email_edit);
        emailView2.setText(user.getEmail());

        //Makes FAB invisible
        FloatingActionButton saveUser = findViewById(R.id.save_user);
        saveUser.setVisibility(View.GONE);
    }

    /**
     * Enters edit mode
     */
    private void editMode() {

        RelativeLayout displayMode = findViewById(R.id.display_mode);
        RelativeLayout editMode = findViewById(R.id.edit_mode);

        //manages visibility of the two layouts
        editMode.setVisibility(View.VISIBLE);
        displayMode.setVisibility(View.GONE);

        TextView emailView = findViewById(R.id.email_2);
        emailView.setText(user.getEmail());

        TextView firstNameView = findViewById(R.id.first_name_edit_2);
        firstNameView.setText(user.getFirstName());

        TextView lastNameView = findViewById(R.id.last_name_edit_2);
        lastNameView.setText(user.getLastName());

        TextView phoneView = findViewById(R.id.phone_edit_2);
        phoneView.setText(user.getTelephone());

        TextView emailView2 = findViewById(R.id.email_edit_2);
        emailView2.setText(user.getEmail());

        //Makes FAB visible
        FloatingActionButton saveUser = findViewById(R.id.save_user);
        saveUser.setVisibility(View.VISIBLE);
    }

    /**
     * Manages the confirmation to delete an account
     */
    private void deleteConfirmation() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

        alertBuilder.setMessage(R.string.delete_user_message)
                .setTitle(R.string.delete_user_title)
                .setPositiveButton(R.string.yes, (dialog, id) -> userViewModel.deleteUser(user, new OnAsyncEventListener() {
                            @Override
                            public void onSuccess() { setDeleteResponse(true); }

                            @Override
                            public void onFailure(Exception e) { setDeleteResponse(false); }
                }))
                .setNegativeButton(R.string.cancel, (dialog, id) -> createToast("Account was not deleted"));
        alertBuilder.show();
    }

    /**
     * Manages response from deleting an account
     * @param response the response from the database update
     */
    private void setDeleteResponse(boolean response) {
        if(response) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent logoutIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(logoutIntent);
            createToast("Account deleted successfully");
        } else
            createToast("Error deleting account");
    }

    /**
     * Manages onclick for save_user_btn
     * @param view the current view
     */
    public void saveUser(View view) {

        EditText firstNameT = findViewById(R.id.first_name_edit_2);
        String firstName = firstNameT.getText().toString();

        EditText lastNameT = findViewById(R.id.last_name_edit_2);
        String lastName = lastNameT.getText().toString();

        TextView phoneT = findViewById(R.id.phone_edit_2);
        String phone = phoneT.getText().toString();

        TextView emailT = findViewById(R.id.email_edit_2);
        String email = emailT.getText().toString();

        EditText pass1T = findViewById(R.id.password_1_edit);
        String pass1 = pass1T.getText().toString();

        EditText pass2T = findViewById(R.id.password_2_edit);
        String pass2 = pass2T.getText().toString();

        //tests for names and password and update
        if(firstName.equals("") || lastName.equals(""))
            createToast("Your name / surname cannot be empty");

        else if(testPasswords(pass1, pass2)){

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setTelephone(phone);
            user.setEmail(email);

            userViewModel.updateUser(user, new OnAsyncEventListener() {
                @Override
                public void onSuccess() { setResponse(true); }

                @Override
                public void onFailure(Exception e) { setResponse(false); }
            });
        }
    }

    /**
     * Tests the state of modified passwords
     * @param pass1 first password
     * @param pass2 second password
     * @return update the user or not
     */
    private boolean testPasswords(String pass1, String pass2) {
        if(pass1.equals("") && pass2.equals(""))
            return true;
        if(pass1.equals(pass2)){
            user.setPassword(pass1);
            return true;
        } else {
            createToast("Your passwords do not match");
            return false;
        }
    }

    /**
     * Manages response from save_user_btn
     * @param response response from user update
     */
    private void setResponse(boolean response) {
        if (response) {
            createToast("Your details have been saved");
            hideKeyboard(this);
            displayMode();
        } else
            createToast("Error saving details");
    }

    /**
     * Create a short toast
     * @param text toast message
     */
    public void createToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT
        );
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_car_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.edit:
                if(displayModeActive)
                    editMode();
                else
                    displayMode();

                displayModeActive = !displayModeActive;
                break;

            case R.id.delete:
                deleteConfirmation();
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    /**
     * Force hides the keyboard in certain instances where we do not change page
     * @param activity the current activity
     */
    public void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
