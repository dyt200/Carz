package com.example.carz.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.example.carz.Database.Entities.Car;
import com.example.carz.Database.Entities.CarSearchParameters;
import com.example.carz.Database.Entities.CarAdapter;
import com.example.carz.R;
import com.example.carz.Viewmodel.CarListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static com.example.carz.Activities.LoginActivity.settingShowMyCars;

/**
 *  Main display for all lists of Cars
 */
public class CarListActivity  extends AppCompatActivity {

    private List<Car> Fcars;
    SharedPreferences sharedPreferences;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private boolean isDrawerOpen;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);

        // Get ActionBar
        getSupportActionBar();

        //Drawer menu items come from a string array and we get the Views
        String[] menuItems = getResources().getStringArray(R.array.drawer_menu_items);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.drawer_list);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, menuItems));

        // Set the custom listener which handles all menu items (switch(pos))
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        ListView carList = findViewById(R.id.carList);
        final Intent detailIntent = new Intent(this, CarDetailActivity.class);

        // get user id from shared preferences
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userKey", "");

        //receives data from any source (eg. main or search activities)
        Intent i = getIntent();
        final String action = (String)i.getSerializableExtra("action");

        //handles different types of car lists (i.e. my cars and all cars)
        if(action != null) {
            switch (action) {
                case "my_cars":
                    setTitle(R.string.myCars);
                    CarListViewModel.MyCarsFactory myCarsFactory = new CarListViewModel.MyCarsFactory(userId, getApplication());
                    CarListViewModel viewModel = ViewModelProviders.of(this, myCarsFactory).get(CarListViewModel.class);
                    viewModel.getCars().observe(this, carEntities -> {
                        if (carEntities != null) {
                            Fcars = carEntities;
                            ArrayAdapter<Car> adapter = new CarAdapter(this, 0, Fcars);
                            carList.setAdapter(adapter);
                        }
                    });
                    break;

                case "search":
                    setTitle(R.string.search_results);
                    CarSearchParameters search = (CarSearchParameters) i.getSerializableExtra("search_parameters");
                    CarListViewModel.SearchCarFactory searchCarFactory = new CarListViewModel.SearchCarFactory(search, getApplication());
                    viewModel = ViewModelProviders.of(this, searchCarFactory).get(CarListViewModel.class);
                    viewModel.getCars().observe(this, carEntities -> {
                        if (carEntities != null) {
                            Fcars = carEntities;
                            ArrayAdapter<Car> adapter = new CarAdapter(this, 0, Fcars);
                            carList.setAdapter(adapter);
                        }
                    });
                    break;


                default:
                    SharedPreferences settings = getSharedPreferences(LoginActivity.SETTINGS, Context.MODE_PRIVATE);
                    boolean showMyCars = settings.getBoolean(settingShowMyCars, false);
                    if(showMyCars) {
                        CarListViewModel.AllCarsFactory allCarFactory = new CarListViewModel.AllCarsFactory(getApplication());
                        viewModel = ViewModelProviders.of(this, allCarFactory).get(CarListViewModel.class);
                        viewModel.getCars().observe(this, carEntities -> {
                            if (carEntities != null) {
                                Fcars = carEntities;
                                ArrayAdapter<Car> adapter = new CarAdapter(this, 0, Fcars);
                                carList.setAdapter(adapter);
                            }
                        });

                    } else {
                        CarListViewModel.AllOtherCarsFactory allCarFactoryNoUser = new CarListViewModel.AllOtherCarsFactory(userId, true, getApplication());
                        viewModel = ViewModelProviders.of(this, allCarFactoryNoUser).get(CarListViewModel.class);
                        viewModel.getCars().observe(this, carEntities -> {
                            if (carEntities != null) {
                                Fcars = carEntities;
                                ArrayAdapter<Car> adapter = new CarAdapter(this, 0, Fcars);
                                carList.setAdapter(adapter);
                            }
                        });
                    }
            }
        }

        //Onclick listener for each car
        carList.setOnItemClickListener((parent, view, position, id) -> {
            Car car = (Car) parent.getItemAtPosition(position);
            String clickedCarId = car.getId();
            detailIntent.putExtra("car_id", clickedCarId);
            startActivity(detailIntent);
        });

        //add car button listener
        FloatingActionButton fab = findViewById(R.id.addCar);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddCarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.car_list_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //actions for each menu button
        switch(item.getItemId()) {

            case R.id.listActionSearch:
                final Intent intent = new Intent(this, SearchParametersActivity.class);
                startActivity(intent);
                return true;

            case R.id.listActionUser:
                if(isDrawerOpen)
                    drawerLayout.closeDrawer(GravityCompat.END);
                else
                    drawerLayout.openDrawer(GravityCompat.END);

                isDrawerOpen = !isDrawerOpen;

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Click listener for Drawer menu that passes its position to a switch
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    Intent myAccountIntent = new Intent(view.getContext(), AccountActivity.class);
                    startActivity(myAccountIntent);
                    break;

                case 1:
                    Intent myCarsIntent = new Intent(view.getContext(), CarListActivity.class);
                    myCarsIntent.putExtra("action", "my_cars");
                    startActivity(myCarsIntent);
                    break;

                case 2:
                    Intent intent1 = new Intent(view.getContext(), AppSettingsActivity.class);
                    startActivity(intent1);
                    break;

                case 3:
                    FirebaseAuth.getInstance().signOut();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent13 = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent13);
                    break;
            }
            drawerList.setItemChecked(position, true);
            drawerLayout.closeDrawer(drawerList);
        }
    }
}
