package com.example.carz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carz.Entities.CarSearchParameters;
import com.example.carz.R;


public class SearchParametersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set title and layout
        setTitle(R.string.search);
        setContentView(R.layout.search_parameters);

        //add back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //create spinners
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

    public void search(View view) {
        //var for finding spinner values by position
        int pos;

        //get type
        Spinner typeT = findViewById(R.id.type_spinner);
        pos = typeT.getSelectedItemPosition();
        int[] typeValues = getResources().getIntArray(R.array.type_values);
        int type = typeValues[pos];

        //get manufacturer
        Spinner makeT = findViewById(R.id.make_spinner);
        pos = makeT.getSelectedItemPosition();
        int[] makeValues = getResources().getIntArray(R.array.make_values);
        int make = makeValues[pos];

        //get min year
        TextView minYearT = findViewById(R.id.min_year);
        int minYear;
        if(minYearT.getText().toString().isEmpty())
            minYear = 0;
        else
            minYear = Integer.parseInt(minYearT.getText().toString());

        //get max year
        TextView maxYearT = findViewById(R.id.max_year);
        int maxYear;
        if(maxYearT.getText().toString().isEmpty())
            maxYear = 0;
        else
            maxYear = Integer.parseInt(maxYearT.getText().toString());

        //get min mileage
        TextView minMileageT = findViewById(R.id.min_mileage);
        int minMileage;
        if(minMileageT.getText().toString().isEmpty())
            minMileage = 0;
        else
            minMileage = Integer.parseInt(minMileageT.getText().toString());

        //get max mileage
        TextView maxMileageT = findViewById(R.id.max_mileage);
        int maxMileage;
        if(maxMileageT.getText().toString().isEmpty())
            maxMileage = 0;
        else
            maxMileage = Integer.parseInt(maxMileageT.getText().toString());

        //create search object
        CarSearchParameters search = new CarSearchParameters(
                type,
                make,
                minMileage,
                maxMileage,
                minYear,
                maxYear
        );

        //pass search results into CarListActivity
        Intent intent = new Intent(this, CarListActivity.class);
        intent.putExtra("action", "search");
        intent.putExtra("search_parameters", search);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
