package com.example.carz.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carz.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CarAdapter extends ArrayAdapter<Car> {

    private Context context;
    private List<Car> cars;
    private ImageView imageT;

    public CarAdapter(Context context, int resource, List<Car> cars) {
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

        //get TextView ids
        TextView titleT = view.findViewById(R.id.listTitle);
        TextView mileageT = view.findViewById(R.id.listMileage);
        TextView manufacturerT = view.findViewById(R.id.listManufacturer);
        TextView yearT = view.findViewById(R.id.listYear);
        TextView priceT = view.findViewById(R.id.listPrice);
        imageT = view.findViewById(R.id.listImage);

        //treat strings
        String mileage = car.getMileage()+" km";
        String price = car.getPrice() + " CHF";

        //set TextView text
        titleT.setText(car.getTitle());
        mileageT.setText(mileage);
        manufacturerT.setText(car.getManufacturerString());
        yearT.setText(String.valueOf(car.getYear()));
        priceT.setText(price);


        //draw images
/*        int imageID = context.getResources().getIdentifier(car.getImage1(), "drawable", context.getPackageName());
        imageT.setImageResource(imageID);*/

        loadImage(car.getImage1());

        return view;
    }

    public void loadImage(String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .into(imageT);
    }


}
