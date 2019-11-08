package com.example.carz.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "image",
    foreignKeys = @ForeignKey(
        entity = Car.class,
        parentColumns = "id",
        childColumns = "car",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("car")}
)
public class CarImage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "car")
    private int car;

    @ColumnInfo(name = "url")
    private String url;

    public CarImage(int car, String url) {
        this.car = car;
        this.url = url;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getCar() { return car; }

    public void setCar(int carId) { this.car = carId; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}
