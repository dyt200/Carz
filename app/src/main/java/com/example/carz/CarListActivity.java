package com.example.carz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carz.Objects.Car;
import com.example.carz.Objects.CarAdapter;
import com.example.carz.Objects.CarList;

import java.util.ArrayList;

public class CarListActivity  extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);
        ListView carList = findViewById(R.id.carList);

        //receives data from any source (eg. main or search activities)
        Intent i = getIntent();
        final ArrayList<Car> cars = (ArrayList<Car>)i.getSerializableExtra("carList");

        //creates instance of customised adapter for listView
        ArrayAdapter<Car> adapter = new CarAdapter(
                this,
                0,
                cars
        );
        carList.setAdapter(adapter);

        final Intent detailIntent = new Intent(this, CarDetailActivity.class);

        //listener for car details
        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Car car = cars.get(position);
               detailIntent.putExtra("carObj", car);
               startActivity(detailIntent);
            }
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
                // custom dialog

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
                appSettingsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), AppSettingsActivity.class);
                        startActivity(intent);
                    }
                });
                TextView myCarsButton = dialogView.findViewById(R.id.my_cars);
                myCarsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CarList cars = new CarList();
                        //TODO: pass the user_id to here somehow (genre de session...?)
                        cars.myCars(1);
                        Intent intent = new Intent(v.getContext(), CarListActivity.class);
                        intent.putExtra("carList", cars.getList());
                        startActivity(intent);
                    }
                });
                TextView logOutButton = dialogView.findViewById(R.id.log_out);
                logOutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                System.out.println("USER MENU OPENS");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
