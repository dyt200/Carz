package com.example.carz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.carz.Entities.Car;
import com.example.carz.R;

public class CarDetailActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    boolean isCarOwner = false;
    boolean editMode = false;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_detail);

        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        int userId = sharedpreferences.getInt("userKey", 0);

        Intent i = getIntent();
        car = (Car) i.getSerializableExtra("carObj");

        if (car.getUser() ==  userId){
            isCarOwner = true;
        }

        String uri = "@drawable/" + car.getImage1();
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        ImageView carImageView = findViewById(R.id.carImage);
        Drawable res = getResources().getDrawable(imageResource);
        carImageView.setImageDrawable(res);

        normalMode();

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
            normalMode();
        }
        return true;
    }

    private void normalMode() {

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

        TextView carDescriptionTextView = findViewById(R.id.description);
        carDescriptionTextView.setText(car.getDescription());

        TextView userPhoneTextView = findViewById(R.id.phone);
        String phone = "Contact number : " + car.getUser();
        userPhoneTextView.setText(phone);

        TextView userMailTextView = findViewById(R.id.mail);
        String mail = "Contact email : " + car.getUser();
        userMailTextView.setText(mail);

    }

    private void editMode() {

        RelativeLayout displayMode = findViewById(R.id.displayMode);
        RelativeLayout editMode = findViewById(R.id.editMode);

        //manages visibility of the two layouts
        displayMode.setVisibility(View.GONE);
        editMode.setVisibility(View.VISIBLE);

        TextView carTitleE = findViewById(R.id.carTitleE);
        carTitleE.setText(car.getTitle());

        TextView carTypeE = findViewById(R.id.carTypeE);
        carTypeE.setText(car.getTypeString(car.getType()));

        TextView carManufacturerE = findViewById(R.id.carManufacturerE);
        carManufacturerE.setText(car.getManufacturerString(car.getManufacturer()));

        TextView carYearE = findViewById(R.id.carYearE);
        String year = Integer.toString(car.getYear());
        carYearE.setText(year);

        TextView carMileageE = findViewById(R.id.carMileageE);
        String mileage = Integer.toString(car.getMileage());
        carMileageE.setText(mileage);

        TextView descriptionE = findViewById(R.id.descriptionE);
        descriptionE.setText(car.getDescription());

        TextView userPhoneTextView = findViewById(R.id.phone_2);
        String phone = "Contact number : " + car.getUser();
        userPhoneTextView.setText(phone);

        TextView userMailTextView = findViewById(R.id.mail_2);
        String mail = "Contact email : " + car.getUser();
        userMailTextView.setText(mail);
    }
}
