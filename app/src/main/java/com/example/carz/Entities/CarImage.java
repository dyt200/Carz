package com.example.carz.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "image")
public class CarImage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "car")
    private int car;

    @ColumnInfo(name = "url")
    private String url;

    public CarImage(int carId, String url) {
        this.car = carId;
        this.url = url;
    }

    public CarImage(int id, int carId, String url) {
        this.car = carId;
        this.url = url;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getCarId() { return car; }

    public void setCarId(int carId) { this.car = carId; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}
