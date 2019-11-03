package com.example.carz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.carz.Entities.Car;
import com.example.carz.Entities.CarAdapter;
import com.example.carz.R;
import com.example.carz.repositories.CarRepository;
import com.example.carz.viewmodel.CarListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CarListActivity  extends AppCompatActivity {

    private CarListViewModel viewModel;
    private CarRepository cr;
    private ArrayList cars;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);
        ListView carList = findViewById(R.id.carList);
        final Intent detailIntent = new Intent(this, CarDetailActivity.class);

        //receives data from any source (eg. main or search activities)
        Intent i = getIntent();
        final String action = (String)i.getSerializableExtra("action");

        //handles different types of car lists (i.e. my cars and all cars)
        switch(action) {
            case "my_cars":
                int userId = (int)i.getSerializableExtra("user_id");
                CarListViewModel.MyCarsFactory myCarsFactory = new CarListViewModel.MyCarsFactory(userId, getApplication());
                    viewModel = ViewModelProviders.of(this, myCarsFactory).get(CarListViewModel.class);
                    viewModel.getCars().observe(this, carEntities -> {
                        if(carEntities != null) {
                            cars = new ArrayList<>(carEntities);
                            ArrayAdapter<Car> adapter = new CarAdapter(this,0,cars);
                            carList.setAdapter(adapter);
                        }
                    });
                break;

            default:
                CarListViewModel.AllCarsFactory allCarsFactory = new CarListViewModel.AllCarsFactory(getApplication());
                viewModel = ViewModelProviders.of(this, allCarsFactory).get(CarListViewModel.class);
                viewModel.getCars().observe(this, carEntities -> {
                    if(carEntities != null) {
                        cars = new ArrayList<>(carEntities);
                        ArrayAdapter<Car> adapter = new CarAdapter(this,0,cars);
                        carList.setAdapter(adapter);
                    }
                });
        }

        //Onclick listener for each car
        carList.setOnItemClickListener((parent, view, position, id) -> {
            cr = CarRepository.getInstance();
            cr.getCarById(position+1, getApplicationContext()).observe(CarListActivity.this, car -> {
                detailIntent.putExtra("carObj", car);
                startActivity(detailIntent);
            });
        });

        //add car button listener
        FloatingActionButton myFab = findViewById(R.id.addCar);
        myFab.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddCarActivity.class);
            startActivity(intent);
        });

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
                final Intent intent = new Intent(this, SearchParametersActivity.class);
                startActivity(intent);
                return true;

            case R.id.listActionUser:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.user_menu_dialog,null);
                builder.setView(dialogView);

                AlertDialog userMenu = builder.create();
                userMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                userMenu.show();

                WindowManager.LayoutParams wmlp = userMenu.getWindow().getAttributes();
                wmlp.gravity = Gravity.TOP | Gravity.END;
                wmlp.width = 550;
                wmlp.x = 50;   //x position
                wmlp.y = 0;   //y position

                userMenu.getWindow().setAttributes(wmlp);


                TextView appSettingsButton = dialogView.findViewById(R.id.app_settings);
                appSettingsButton.setOnClickListener(v -> {
                    Intent intent1 = new Intent(v.getContext(), AppSettingsActivity.class);
                    startActivity(intent1);
                });

                TextView myCarsButton = dialogView.findViewById(R.id.my_cars);
                myCarsButton.setOnClickListener(v -> {
                    Intent myCarsIntent = new Intent(v.getContext(), CarListActivity.class);
                    myCarsIntent.putExtra("action", "my_cars");
                    //TODO replace the int 1 below with the user_id of the current session!
                    //right now we only show user_id 1's cars
                    myCarsIntent.putExtra("user_id", 1);
                    startActivity(myCarsIntent);
                });

                TextView logOutButton = dialogView.findViewById(R.id.log_out);
                logOutButton.setOnClickListener(v -> {
                    Intent intent13 = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent13);
                });

                System.out.println("USER MENU OPENS");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
