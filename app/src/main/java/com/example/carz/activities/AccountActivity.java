package com.example.carz.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class AccountActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    UserViewModel userViewModel;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.my_account);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        //add back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        final int userId = sharedPreferences.getInt("userKey", 0);

        Button myCarsBtn = findViewById(R.id.my_cars_btn);
        myCarsBtn.setOnClickListener(view -> {
            Intent myCarsIntent = new Intent(view.getContext(), CarListActivity.class);
            myCarsIntent.putExtra("action", "my_cars");
            startActivity(myCarsIntent);
        });

        UserViewModel.UserFromIdFactory userFactory = new UserViewModel.UserFromIdFactory(getApplication(), userId);
        userViewModel = ViewModelProviders.of(this, userFactory).get(UserViewModel.class);
        userViewModel.getUser().observe(this, userData -> {
            if(userData != null) {
                user = userData;
                displayMode();
            }
        });
    }

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
    }

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

        //Makes FAB visible
        FloatingActionButton saveUser = findViewById(R.id.save_user);
        saveUser.setVisibility(View.VISIBLE);
    }

    private void deleteConfirmation() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.delete_user_message)
                .setTitle(R.string.delete_user_title)
                .setPositiveButton(R.string.yes, (dialog, id) -> createToast("deleted account (not really lol)"))
                .setNegativeButton(R.string.cancel, (dialog, id) -> createToast("Account was not deleted"));
        alertBuilder.show();
    }

    public void saveUser(View view) {
        System.out.println("SAVING USER");
    }

    private void createToast(String text) {
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
                editMode();
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
}
