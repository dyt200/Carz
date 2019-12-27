package com.example.carz.db.pojo;

import com.example.carz.Entities.Car;
import com.example.carz.Entities.CarImage;

import java.util.List;

public class CarWithImages {
    private Car car;

    private List<CarImage> images;

    public Car getCar() { return car; }

    public void setCar(Car car) { this.car = car; }

    public List<CarImage> getImages() { return images; }

    public void setImages(List<CarImage> images) { this.images = images; }
}
