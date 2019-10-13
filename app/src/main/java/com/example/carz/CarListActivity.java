package com.example.carz;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carz.Objects.Car;
import com.example.carz.Objects.CarAdapter;

import java.util.ArrayList;

public class CarListActivity  extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);
        ListView carList = findViewById(R.id.carList);

        //test data
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

        // Get ActionBar
        getSupportActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.car_list_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.listActionSearch:
                System.out.println("SEARCH MENU OPENS");
                return true;

            case R.id.listActionUser:
                System.out.println("USER MENU OPENS");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
