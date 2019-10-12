package com.example.carz.Objects;

public class Car {
    private int id;
    private int manufacturer;
    private int type;
    private int user;
    private int year;
    private int mileage;
    private String title;
    private String description;
    private String condition;
    private String image1;
    private String image2;

    public Car (int id, int type, int manufacturer, int user, int year, int mileage, String title, String description, String condition, String image1, String image2) {
        this.id = id;
        this.type = type;
        this.manufacturer = manufacturer;
        this.user = user;
        this.year = year;
        this.mileage = mileage;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
