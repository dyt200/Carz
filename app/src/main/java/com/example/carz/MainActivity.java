package com.example.carz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.carz.Objects.Car;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);
        ListView carList = findViewById(R.id.carList);

        ArrayList<Car> cars = new ArrayList<>();
        cars.add( new Car(1, 1, 1, 1,2015, 85000, "BMW 6 Series", "","","car_example_1",""));
        cars.add( new Car(2, 1, 2, 1,2008, 126500, "Skoda Octavia 2008 TDI", "","","car_example_2",""));
        cars.add( new Car(3, 1, 2, 1,1999, 250000, "Skoda Octavia", "","","car_example_3",""));
        cars.add( new Car(4, 1, 3, 1,2001, 180000, "Opal Corsa 1.2L", "","","car_example_4",""));
        cars.add( new Car(5, 1, 1, 1,2010, 95000, "BMW Coupé 2010", "","","car_example_1",""));
        cars.add( new Car(6, 1, 1, 1,2015, 85000, "BMW 6 Series", "","","car_example_1",""));
        cars.add( new Car(7, 1, 2, 1,2008, 126500, "Skoda Octavia 2008 TDI", "","","car_example_2",""));
        cars.add( new Car(8, 1, 2, 1,1999, 250000, "Skoda Octavia", "","","car_example_3",""));
        cars.add( new Car(9, 1, 3, 1,2001, 180000, "Opal Corsa 1.2L", "","","car_example_4",""));
        cars.add( new Car(10, 1, 1, 1,2010, 95000, "BMW Coupé 2010", "","","car_example_1",""));
        cars.add( new Car(11, 1, 1, 1,2015, 85000, "BMW 6 Series", "","","car_example_1",""));
        cars.add( new Car(12, 1, 2, 1,2008, 126500, "Skoda Octavia 2008 TDI", "","","car_example_2",""));
        cars.add( new Car(13, 1, 2, 1,1999, 250000, "Skoda Octavia", "","","car_example_3",""));
        cars.add( new Car(14, 1, 3, 1,2001, 180000, "Opal Corsa 1.2L", "","","car_example_4",""));
        cars.add( new Car(15, 1, 1, 1,2010, 95000, "BMW Coupé 2010", "","","car_example_1",""));

        ArrayAdapter<Car> adapter = new CarAdapter(
                this,
                0,
                cars
        );

        carList.setAdapter(adapter);
    }
}
