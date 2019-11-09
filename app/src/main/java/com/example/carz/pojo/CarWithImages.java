package com.example.carz.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.carz.Entities.Car;
import com.example.carz.Entities.CarImage;

import java.util.List;

public class CarWithImages {
    @Embedded
    private Car car;
    @Relation(parentColumn = "id", entityColumn = "car", entity = CarImage.class)
    private List<CarImage> images;

    public Car getCar() { return car; }

    public void setCar(Car car) { this.car = car; }

    public List<CarImage> getImages() { return images; }

    public void setImages(List<CarImage> images) { this.images = images; }
}
