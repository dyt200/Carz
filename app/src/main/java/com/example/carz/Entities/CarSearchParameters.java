package com.example.carz.Entities;

import androidx.sqlite.db.SimpleSQLiteQuery;

import java.io.Serializable;

public class CarSearchParameters implements Serializable {

    private int type;
    private int manufacturer;
    private int minMileage;
    private int maxMileage;
    private int minYear;
    private int maxYear;

    public CarSearchParameters(
            int type,
            int manufacturer,
            int minMileage,
            int maxMileage,
            int minYear,
            int maxYear
    ) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.minMileage = minMileage;
        this.maxMileage = maxMileage;
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    public SimpleSQLiteQuery getDatabaseQuery() {
        String query = "SELECT * FROM car ";
        boolean firstParam = true;

        if(type > 0 || manufacturer > 0 || minMileage > 0|| maxMileage > 0|| minYear > 0|| maxYear > 0)
            query += "WHERE ";

        if(type > 0) {
            query += "type = "+type+" ";
            firstParam = false;
        }

        if(manufacturer > 0) {
            if(!firstParam) query += "AND ";
            query += "manufacturer = "+manufacturer+" ";
            firstParam = false;
        }

        if(minMileage > 0) {
            if(!firstParam) query += "AND ";
            query += "mileage >= "+minMileage+" ";
            firstParam = false;
        }

        if(maxMileage > 0) {
            if(!firstParam) query += "AND ";
            query += "mileage <= "+maxMileage+" ";
            firstParam = false;
        }

        if(minYear > 0) {
            if(!firstParam) query += "AND ";
            query += "year >= "+minYear+" ";
            firstParam = false;
        }

        if(maxYear > 0) {
            if(!firstParam) query += "AND ";
            query += "year <= "+maxYear+" ";
            firstParam = false;
        }
        return new SimpleSQLiteQuery(query);
    }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public int getManufacturer() { return manufacturer; }

    public void setManufacturer(int manufacturer) { this.manufacturer = manufacturer; }

    public int getMinMileage() { return minMileage; }

    public void setMinMileage(int minMileage) { this.minMileage = minMileage; }

    public int getMaxMileage() { return maxMileage; }

    public void setMaxMileage(int maxMileage) { this.maxMileage = maxMileage; }

    public int getMinYear() { return minYear; }

    public void setMinYear(int minYear) { this.minYear = minYear; }

    public int getMaxYear() { return maxYear; }

    public void setMaxYear(int maxYear) { this.maxYear = maxYear; }
}
