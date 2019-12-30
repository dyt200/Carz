package com.example.carz.db.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carz.Entities.Car;
import com.example.carz.Entities.CarImage;
import com.example.carz.R;
import com.example.carz.pojo.CarWithImages;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FCarAdapter extends ArrayAdapter<FCar> {

    private Context context;
    private List<FCar> cars;
    private ImageView imageT;

    public FCarAdapter(Context context, int resource, List<FCar> cars) {
        super(context, resource, cars);
        this.context = context;
        this.cars = cars;
    }

    @NotNull
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        FCar car = cars.get(position);
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
        String mileage = car.getFormattedMileageString();
        String price = car.getFormattedPriceString();

        //set TextView text
        titleT.setText(car.getTitle());
        mileageT.setText(mileage);
        manufacturerT.setText(car.getManufacturerString());
        yearT.setText(String.valueOf(car.getYear()));
        priceT.setText(price);

        loadImage(car.getImages().get(0));

        return view;
    }

    private void loadImage(String url) {
        Glide.with(context)
                .load(url)
                .into(imageT);
    }
}
