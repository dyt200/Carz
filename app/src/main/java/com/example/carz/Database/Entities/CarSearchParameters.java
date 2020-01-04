package com.example.carz.Database.Entities;
import java.io.Serializable;

public class CarSearchParameters implements Serializable {

    private int type;
    private int manufacturer;
    private int minMileage;
    private int maxMileage;
    private int minYear;
    private int maxYear;
    private String model;


    public CarSearchParameters(
            int type,
            int manufacturer,
            int minMileage,
            int maxMileage,
            int minYear,
            int maxYear,
            String model
    ) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.minMileage = minMileage;
        this.maxMileage = maxMileage;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.model = model;
    }

    public int getType() { return type; }

    public int getManufacturer() { return manufacturer; }

    public int getMinMileage() { return minMileage; }

    public int getMaxMileage() { return maxMileage; }

    public int getMinYear() { return minYear; }

    public int getMaxYear() { return maxYear; }

    public String getModel() { return model; }

}
