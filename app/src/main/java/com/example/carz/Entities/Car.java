package com.example.carz.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "car")
public class Car implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "manufacturer")
    private int manufacturer;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "user")
    private int user;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "mileage")
    private int mileage;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "condition")
    private String condition;

    @ColumnInfo(name = "image1")
    private String image1;

    @ColumnInfo(name = "image2")
    private String image2;

    public Car (int type, int manufacturer, int user, int price, int year, int mileage, String model, String description, String condition, String image1, String image2) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.user = user;
        this.price = price;
        this.year = year;
        this.mileage = mileage;
        this.model = model;
        this.description = description;
        this.condition = condition;
        this.image1 = image1;
        this.image2 = image2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) { this.type = type; }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getModel() { return model; }

    public void setModel(String model) { this.model = model; }

    public String getTitle() {
        return getManufacturerString()+" "+getTypeString()+" "+model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) { this.image2 = image2; }

    public void ToString() {
        System.out.println(", Type : "+type+", Make : "+manufacturer+", Year : "+year+", Mileage : "+mileage);
    }

    public String getManufacturerString() {
        String string;
        switch(this.manufacturer) {
            case 1:     string = "Skoda";   break;
            case 2:     string = "BMW";     break;
            case 3:     string = "Opel";    break;
            default:    string = "ERROR";
        }
        return string;
    }

    public String getTypeString() {
        String string;
        switch(this.type) {
            case 1:     string = "Family";   break;
            case 2:     string = "Coupé";     break;
            case 3:     string = "Cabriolet";    break;
            default:    string = "ERROR";
        }
        return string;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
