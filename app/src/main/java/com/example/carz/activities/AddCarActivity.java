package com.example.carz.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carz.Entities.Car;
import com.example.carz.R;
import com.example.carz.repositories.CarRepository;
import com.example.carz.util.OnAsyncEventListener;

public class AddCarActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

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
    }

    public void addCar(View view) {
        int pos;

        // get user id from shared preferences
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        int userId = sharedpreferences.getInt("userKey", 0);

        Spinner manufacturerT = findViewById(R.id.make_spinner);
        pos = manufacturerT.getSelectedItemPosition();
        int[] makeValues = getResources().getIntArray(R.array.make_values);
        int manufacturer = makeValues[pos];

        Spinner typeT = findViewById(R.id.type_spinner);
        pos = typeT.getSelectedItemPosition();
        int[] typeValues = getResources().getIntArray(R.array.make_values);
        int type = typeValues[pos];

        EditText modelT = findViewById(R.id.model);
        String model = modelT.getText().toString();

        EditText mileageT = findViewById(R.id.mileage);
        int mileage = Integer.parseInt(mileageT.getText().toString());

        EditText yearT = findViewById(R.id.year);
        int year = Integer.parseInt(yearT.getText().toString());

        EditText descT = findViewById(R.id.description);
        String desc = descT.getText().toString();

        EditText conditionT = findViewById(R.id.condition);
        String condition = conditionT.getText().toString();

        EditText priceT = findViewById(R.id.price);
        int price = Integer.parseInt(priceT.getText().toString());

        //check that all fields have been completed
        if(     manufacturer == 0
                || type == 0
                || model.equals("")
                || mileage == 0
                || year == 0
                || desc.equals("")
                || condition.equals("")
                || price == 0

        ) createToast("Please fill in all of the fields !");
        else
            insertCar(
                    new Car(
                            type,
                            manufacturer,
                            userId,
                            price,
                            year,
                            mileage,
                            model,
                            desc,
                            condition,
                            "car_example_2",
                            ""
                    ),
                    view
            );
    }

    //process to insert a car
    private void insertCar(Car car, View view) {
        CarRepository cr = CarRepository.getInstance();
        cr.insert(car, new OnAsyncEventListener() {

            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) { setResponse(false); }

        }, view.getContext());
    }

    //manages responses for insertion of cars
    private void setResponse(boolean response) {
        if(response) {
            Intent intent = new Intent(this, CarListActivity.class);
            intent.putExtra("action", "my_cars");
            startActivity(intent);
            createToast("You car was added successfully!");
        } else
            createToast("Failed to create this car !");
    }

    private void createToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT
        );
        toast.show();
    }
}
