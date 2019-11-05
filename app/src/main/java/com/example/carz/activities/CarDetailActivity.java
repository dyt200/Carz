package com.example.carz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import com.example.carz.Entities.Car;
import com.example.carz.Entities.CarAdapter;
import com.example.carz.Entities.User;
import com.example.carz.R;
import com.example.carz.repositories.CarRepository;
import com.example.carz.util.OnAsyncEventListener;
import com.example.carz.viewmodel.CarListViewModel;
import com.example.carz.viewmodel.CarViewModel;
import com.example.carz.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CarDetailActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    CarViewModel carViewModel;
    UserViewModel userViewModel;

    boolean isCarOwner = false;
    boolean editMode = false;

    Car car;
    User carUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_detail);

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

        Intent i = getIntent();
        final int carId = (int) i.getSerializableExtra("car_id");

        CarViewModel.Factory carFactory = new CarViewModel.Factory(getApplication(), carId);
        carViewModel = ViewModelProviders.of(this, carFactory).get(CarViewModel.class);
        carViewModel.getCar().observe(this, carData -> {
            if (carData != null) {

                car = carData;
                final int carOwner = car.getUser();

                UserViewModel.UserFromIdFactory userFactory = new UserViewModel.UserFromIdFactory(getApplication(), carOwner);
                userViewModel = ViewModelProviders.of(this, userFactory).get(UserViewModel.class);
                userViewModel.getUser().observe(this, user -> {
                    if (car != null) {
                        this.carUser = user;

                        if (carOwner == userId) isCarOwner = true;

                        String uri = "@drawable/" + car.getImage1();
                        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

                        ImageView carImageView = findViewById(R.id.carImage);
                        Drawable res = ContextCompat.getDrawable(this, imageResource);
                        carImageView.setImageDrawable(res);

                        displayMode();
                    }
                });
            }
        });
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
        if (item.getItemId() == R.id.editCar && !editMode){
            editMode = true;
            editMode();
        } else {
            editMode = false;
            displayMode();
        }
        return true;
    }

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
        String type = car.getTypeString(car.getType());
        carTypeTextView.setText(type);

        TextView carManufacturerTextView = findViewById(R.id.carManufacturer);
        String manufacturer = car.getManufacturerString(car.getManufacturer());
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
        String phone = "Contact number : " + car.getUser();
        userPhoneTextView.setText(phone);

        TextView userMailTextView = findViewById(R.id.mail);
        String mail = "Contact email : " + car.getUser();
        userMailTextView.setText(mail);

        //Makes FAB invisible (just in case)
        FloatingActionButton saveCar = findViewById(R.id.saveCar);
        saveCar.setVisibility(View.INVISIBLE);

    }

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

        TextView carYearE = findViewById(R.id.carYearE);
        String year = Integer.toString(car.getYear());
        carYearE.setText(year);

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

        TextView carYearE = findViewById(R.id.carYearE);
        car.setYear(Integer.parseInt(carYearE.getText().toString()));

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

    public void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
