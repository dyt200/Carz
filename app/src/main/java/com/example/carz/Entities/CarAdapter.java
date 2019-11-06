package com.example.carz.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.carz.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CarAdapter extends ArrayAdapter<Car> {

    private Context context;
    private ArrayList<Car> cars;

    public CarAdapter(Context context, int resource, ArrayList<Car> cars) {
        super(context, resource, cars);
        this.context = context;
        this.cars = cars;
    }

    @NotNull
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        Car car = cars.get(position);
        View view;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.car_list_item, parent, false);
        } else
            view = convertView;

        TextView titleT = view.findViewById(R.id.listTitle);
        TextView mileageT = view.findViewById(R.id.listMileage);
        TextView manufacturerT = view.findViewById(R.id.listManufacturer);
        TextView yearT = view.findViewById(R.id.listYear);
        TextView priceT = view.findViewById(R.id.listPrice);
        ImageView imageT = view.findViewById(R.id.listImage);

        String title = car.getTitle();
        String mileage = car.getMileage()+" km";
        String year = String.valueOf(car.getYear());
        String price = car.getPrice() + " CHF";
        String manufacturer = getManufacturer(car.getManufacturer());

        titleT.setText(title);
        mileageT.setText(mileage);
        manufacturerT.setText(manufacturer);
        yearT.setText(year);
        priceT.setText(price);

        int imageID = context.getResources().getIdentifier(car.getImage1(), "drawable", context.getPackageName());
        imageT.setImageResource(imageID);

        return view;
    }

    //TODO : temp, replace with something else!
    private String getManufacturer(int id) {

        String string;

        switch(id) {
            case 1:     string = "Skoda";   break;
            case 2:     string = "BMW";     break;
            case 3:     string = "Opel";    break;
            default:    string = "ERROR";
        }
        return string;
    }
}
