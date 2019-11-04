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
        TextView carTitleTextView = findViewById(R.id.carTitle);
        carTitleTextView.setText(car.getTitle());
        carTitleTextView.setVisibility(View.VISIBLE);

        TextView editCarTitleTextView = findViewById(R.id.editCarTitle);
        editCarTitleTextView.setVisibility(View.GONE);

        TextView carTypeTextView = findViewById(R.id.carType);
        String type = car.getTypeString(car.getType());
        carTypeTextView.setText(type);
        carTitleTextView.setVisibility(View.VISIBLE);

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
        String phone = "Phone of user: " + car.getUser();
        userPhoneTextView.setText(phone);

        TextView userMailTextView = findViewById(R.id.mail);
        String mail = "Mail of user: " + car.getUser();
        userMailTextView.setText(mail);

    }

    private void editMode() {

        // remove all the existing XML, not very pretty

        TextView carTitleTextView = findViewById(R.id.carTitle);
        carTitleTextView.setVisibility(View.GONE);

        TextView carTypeT = findViewById(R.id.carTypeT);
        carTypeT.setVisibility(View.GONE);
        TextView carTypeTextView = findViewById(R.id.carType);
        carTypeTextView.setVisibility(View.GONE);

        TextView carManufacturerT = findViewById(R.id.carManufacturerT);
        carManufacturerT.setVisibility(View.GONE);
        TextView carManufacturerTextView = findViewById(R.id.carManufacturer);
        carManufacturerTextView.setVisibility(View.GONE);

        TextView carYearT = findViewById(R.id.carYearT);
        carYearT.setVisibility(View.GONE);
        TextView carYearTextView = findViewById(R.id.carYear);
        carYearTextView.setVisibility(View.GONE);

        TextView carMileageT = findViewById(R.id.carMileageT);
        carMileageT.setVisibility(View.GONE);
        TextView carMileageTextView = findViewById(R.id.carMileage);
        carMileageTextView.setVisibility(View.GONE);

        TextView descriptionLabel = findViewById(R.id.descriptionLabel);
        descriptionLabel.setVisibility(View.GONE);
        TextView carDescriptionTextView = findViewById(R.id.description);
        carDescriptionTextView.setVisibility(View.GONE);

        TextView contactLabel = findViewById(R.id.contactLabel);
        contactLabel.setVisibility(View.GONE);
        TextView userPhoneTextView = findViewById(R.id.phone);
        userPhoneTextView.setVisibility(View.GONE);
        TextView userMailTextView = findViewById(R.id.mail);
        userMailTextView.setVisibility(View.GONE);

        // Display the form in the edit mode
        TextView editCarTitleTextView = findViewById(R.id.editCarTitle);
        editCarTitleTextView.setVisibility(View.VISIBLE);
        editCarTitleTextView.setText(car.getTitle());
    }
}
