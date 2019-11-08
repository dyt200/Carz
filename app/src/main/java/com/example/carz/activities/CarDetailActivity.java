package com.example.carz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.carz.Entities.Car;
import com.example.carz.pojo.CarWithImages;
import com.example.carz.Entities.User;
import com.example.carz.R;
import com.example.carz.util.OnAsyncEventListener;
import com.example.carz.viewmodel.CarViewModel;
import com.example.carz.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class CarDetailActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    CarViewModel carViewModel;
    UserViewModel userViewModel;

    boolean isCarOwner = false;
    boolean editMode = false;

    CarWithImages carI;
    Car car;
    User carUser;

    private ImageView carImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_detail);

        //add back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get user session
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        int userId = sharedpreferences.getInt("userKey", 0);

        //Building spinners
        Spinner typeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.type_array,
                android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        Spinner makeSpinner = findViewById(R.id.make_spinner);
        ArrayAdapter<CharSequence> makeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.make_array,
                android.R.layout.simple_spinner_item
        );
        makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setAdapter(makeAdapter);

        //populate year spinner with years from 1950 -> present
        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1990; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        Spinner spinYear = findViewById(R.id.year_spinner);
        spinYear.setAdapter(adapter);

        Intent i = getIntent();
        final int carId = (int) i.getSerializableExtra("car_id");

        CarViewModel.Factory carFactory = new CarViewModel.Factory(getApplication(), carId);
        carViewModel = ViewModelProviders.of(this, carFactory).get(CarViewModel.class);
        carViewModel.getCar().observe(this, carData -> {
            if (carData != null) {
                carI = carData;
                car = carI.getCar();
                final int carOwner = car.getUser();

                UserViewModel.UserFromIdFactory userFactory = new UserViewModel.UserFromIdFactory(getApplication(), carOwner);
                userViewModel = ViewModelProviders.of(this, userFactory).get(UserViewModel.class);
                userViewModel.getUser().observe(this, user -> {
                    if (user != null) {
                        this.carUser = user;

                        if (carOwner == userId)
                            isCarOwner = true;

                        carImageView = findViewById(R.id.carImage);
                        loadImage(car.getImage1());
                        displayMode();
                    }
                });
            }
        });
    }

    public void loadImage(String imgUrl) {
        Glide.with(getApplicationContext())
                .load(imgUrl)
                .into(carImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCarOwner) {
            getMenuInflater().inflate(R.menu.user_car_action_bar, menu);
            return super.onCreateOptionsMenu(menu);
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.edit:
                editMode = true;
                editMode();
                break;

            case R.id.delete:
                deleteConfirmation();
                break;
        }
        return true;
    }

    /**
     * Initialises the display version of CarDetails
     */
    private void displayMode() {

        RelativeLayout displayMode = findViewById(R.id.displayMode);
        RelativeLayout editMode = findViewById(R.id.editMode);

        //manages visibility of the two layouts
        editMode.setVisibility(View.GONE);
        displayMode.setVisibility(View.VISIBLE);

        TextView carTitleTextView = findViewById(R.id.carTitle);
        carTitleTextView.setText(car.getTitle());
        carTitleTextView.setVisibility(View.VISIBLE);

        TextView carTypeTextView = findViewById(R.id.carType);
        String type = car.getTypeString();
        carTypeTextView.setText(type);

        TextView carManufacturerTextView = findViewById(R.id.carManufacturer);
        String manufacturer = car.getManufacturerString();
        carManufacturerTextView.setText(manufacturer);

        TextView carYearTextView = findViewById(R.id.carYear);
        String year =  Integer.toString(car.getYear());
        carYearTextView.setText(year);

        TextView carMileageTextView = findViewById(R.id.carMileage);
        String mileage = car.getMileage()+" km";
        carMileageTextView.setText(mileage);

        TextView carModelTextView = findViewById(R.id.carModel);
        carModelTextView.setText(car.getModel());

        TextView carDescriptionTextView = findViewById(R.id.description);
        carDescriptionTextView.setText(car.getDescription());

        TextView carConditionTextView = findViewById(R.id.condition);
        carConditionTextView.setText(car.getCondition());

        TextView userPhoneTextView = findViewById(R.id.phone);
        String phone = "Contact number : " + carUser.getTelephone();
        userPhoneTextView.setText(phone);

        TextView userMailTextView = findViewById(R.id.mail);
        String mail = "Contact email : " + carUser.getEmail();
        userMailTextView.setText(mail);

        //Makes FAB invisible (just in case)
        FloatingActionButton saveCar = findViewById(R.id.saveCar);
        saveCar.setVisibility(View.INVISIBLE);

    }

    /**
     * Initialises the editable version of CarDetails
     */
    private void editMode() {

        RelativeLayout displayMode = findViewById(R.id.displayMode);
        RelativeLayout editMode = findViewById(R.id.editMode);

        //manages visibility of the two layouts
        displayMode.setVisibility(View.GONE);
        editMode.setVisibility(View.VISIBLE);

        TextView carTitleE = findViewById(R.id.carTitle_2);
        carTitleE.setText(car.getTitle());

        Spinner typeSpinner = findViewById(R.id.type_spinner);
        typeSpinner.setSelection(car.getType());

        Spinner makeSpinner = findViewById(R.id.make_spinner);
        makeSpinner.setSelection(car.getManufacturer());

        Spinner yearSpinner = findViewById(R.id.year_spinner);
        ArrayAdapter adapter = (ArrayAdapter)yearSpinner.getAdapter();
        yearSpinner.setSelection(adapter.getPosition(car.getYear()));

        TextView carMileageE = findViewById(R.id.carMileageE);
        String mileage = Integer.toString(car.getMileage());
        carMileageE.setText(mileage);

        TextView carModelE = findViewById(R.id.carModelE);
        carModelE.setText(car.getModel());

        TextView descriptionE = findViewById(R.id.descriptionE);
        descriptionE.setText(car.getDescription());

        TextView conditionE = findViewById(R.id.conditionE);
        conditionE.setText(car.getCondition());

        TextView userPhoneTextView = findViewById(R.id.phone_2);
        String phone = "Contact number : " + car.getUser();
        userPhoneTextView.setText(phone);

        TextView userMailTextView = findViewById(R.id.mail_2);
        String mail = "Contact email : " + car.getUser();
        userMailTextView.setText(mail);

        //Makes FAB visible
        FloatingActionButton saveCar = findViewById(R.id.saveCar);
        saveCar.setVisibility(View.VISIBLE);
    }

    /**
     * Rebuilds and updates the current car object to the DB
     * @param view the current view
     */
    public void saveCar(View view) {
        int pos;

        //update car attribute with new details
        Spinner typeT = findViewById(R.id.type_spinner);
        pos = typeT.getSelectedItemPosition();
        int[] typeValues = getResources().getIntArray(R.array.make_values);
        car.setType(typeValues[pos]);

        Spinner makeT = findViewById(R.id.make_spinner);
        pos = makeT.getSelectedItemPosition();
        int[] makeValues = getResources().getIntArray(R.array.make_values);
        car.setManufacturer(makeValues[pos]);

        Spinner yearSpinner = findViewById(R.id.year_spinner);
        car.setYear(Integer.parseInt(yearSpinner.getSelectedItem().toString()));

        TextView carMileageE = findViewById(R.id.carMileageE);
        car.setMileage(Integer.parseInt(carMileageE.getText().toString()));

        TextView carModelE = findViewById(R.id.carModelE);
        car.setModel(carModelE.getText().toString());

        TextView descriptionE = findViewById(R.id.descriptionE);
        car.setDescription(descriptionE.getText().toString());

        TextView conditionE = findViewById(R.id.conditionE);
        car.setCondition(conditionE.getText().toString());

        carViewModel.updateCar(car, new OnAsyncEventListener() {
            @Override
            public void onSuccess() { setResponse(true); }

            @Override
            public void onFailure(Exception e) { setResponse(false); }
        });
    }

    private void setResponse(boolean response) {
        if (response) {
            createToast("Car has been saved");
            hideKeyboard(this);
            displayMode();
        } else
            createToast("Error saving car");
    }

    private void createToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT
        );
        toast.show();
    }

    public void deleteConfirmation() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.delete_car_message)
        .setTitle(R.string.delete_car_title)
        .setPositiveButton(R.string.yes, (dialog, id) -> carViewModel.deleteCar(car, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                onBackPressed();
                setDeleteResponse(true);
            }

            @Override
            public void onFailure(Exception e) { setDeleteResponse(false); }
        }))
        .setNegativeButton(R.string.cancel, (dialog, id) -> createToast("Car deletion cancelled"));
        alertBuilder.show();
    }

    private void setDeleteResponse(boolean response) {
        if (response) {
            createToast("Car has been has ben successfully deleted");
            hideKeyboard(this);
            displayMode();
        } else
            createToast("Error deleting car");
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

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
