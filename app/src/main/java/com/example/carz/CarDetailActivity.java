package com.example.carz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carz.Objects.Car;

public class CarDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_detail);

        Intent i = getIntent();
        Car car = (Car) i.getSerializableExtra("carObj");


        String uri = "@drawable/" + car.getImage1();
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        ImageView carImageView = findViewById(R.id.carImage);
        Drawable res = getResources().getDrawable(imageResource);
        carImageView.setImageDrawable(res);

        TextView carTitleTextView = findViewById(R.id.carTitle);
        carTitleTextView.setText(car.getTitle());

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
        String phone = "Phone of user: " + car.getUser();
        userPhoneTextView.setText(phone);

        TextView userMailTextView = findViewById(R.id.mail);
        String mail = "Mail of user: " + car.getUser();
        userMailTextView.setText(mail);
    }
}
