package com.example.carz.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carz.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<CarImage> carImages;

    public ImageSliderAdapter(Context context, List<CarImage> carImages) {
        this.context = context;
        this.carImages = carImages;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        viewHolder.textViewDescription.setText("Carz™");
        for (CarImage carImage: carImages){
            if (position == carImages.indexOf(carImage)){
                Glide.with(viewHolder.itemView)
                        .load(carImage.getUrl())
                        .into(viewHolder.imageViewBackground);
            }
        }
    }

    @Override
    public int getCount() {
        return carImages.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
