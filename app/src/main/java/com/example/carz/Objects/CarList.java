package com.example.carz.Objects;

import java.util.ArrayList;
import java.util.Iterator;

public class CarList {
    public ArrayList<Car> list;

    public CarList() {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add( new Car(1, 1, 1, 1,15000, 2015, 85000,  "6 Series","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1",""));
        cars.add( new Car(2, 2, 2, 2,10000,2008, 126500,  "Octavia","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_2",""));
        cars.add( new Car(3, 2, 2, 3,12500,1999, 250000,  "6 Series","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_3",""));
        cars.add( new Car(4, 1, 3, 4,25000,2001, 180000,  "Octavia","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_4",""));
        cars.add( new Car(5, 1, 1, 5,30000,2010, 95000,  "Octavia","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1",""));
        cars.add( new Car(6, 3, 1, 2,15000,2015, 85000,  "Super Sport","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1",""));
        cars.add( new Car(7, 1, 2, 4,15000,2008, 126500,  "6 Series","","","car_example_2",""));
        cars.add( new Car(8, 3, 2, 5,15000,1999, 250000,  "6 Series","","","car_example_3",""));
        cars.add( new Car(9, 2, 3, 5,25000,2001, 180000, "6 Series","","","car_example_4",""));
        cars.add( new Car(10, 2, 1, 2,15000,2010, 95000,  "Octavia","","","car_example_1",""));
        cars.add( new Car(11, 1, 1, 2,10000,2015, 85000,  "Super Sport","","","car_example_1",""));
        cars.add( new Car(12, 1, 2, 3,12000,2008, 126500,  "Super Sport","","","car_example_2",""));
        cars.add( new Car(13, 3, 2, 4,11000,1999, 250000,  "Super Sport","","","car_example_3",""));
        cars.add( new Car(14, 2, 3, 3,13500,2001, 180000,  "6 Series","","","car_example_4",""));
        cars.add( new Car(15, 2, 1, 1,15000,2010, 95000,  "6 Series","","","car_example_1",""));
        this.list = cars;
    }

    public ArrayList<Car> getList() {
        return list;
    }

    public void filter(int type, int manufacturer, int yearMin, int yearMax, int mileageMin, int mileageMax) {
        filterType(type);
        filterManufacturer(manufacturer);
        filterYear(yearMin, yearMax);
        filterMileage(mileageMin, mileageMax);
    }

    private void filterType(int type) {
        if(type != 0) {
            Iterator i = list.iterator();
            while (i.hasNext()) {
                Car car = (Car) i.next();
                if (car.getType() != type) {
                    i.remove();
                }
            }
        }
    }

    private void filterManufacturer(int type) {
        if(type > 0) {
            Iterator i = list.iterator();
            while(i.hasNext()) {
                Car car = (Car)i.next();
                if (car.getManufacturer() != type) {
                    i.remove();
                }
            }
        }
    }

    private void filterMileage(int min, int max) {
        int mileage;
        Iterator i = list.iterator();

        while(i.hasNext()) {
            Car car = (Car) i.next();
            mileage = car.getMileage();

            if(max > 0 && mileage > max)
                i.remove();
            if(min > 0 && mileage < min)
                i.remove();
        }
    }

    private void filterYear(int min, int max) {
        int year;
        Iterator i = list.iterator();
        while(i.hasNext()) {
            Car car = (Car) i.next();
            year = car.getYear();

            if(max > 0 && year > max)
                i.remove();
            if(min > 0 && year < min)
                i.remove();
        }
    }

    public void myCars(int user) {
        int userId;
        Iterator i = list.iterator();
        while(i.hasNext()) {
            Car car = (Car) i.next();
            userId = car.getUser();

            if(userId != user)
                i.remove();
        }
    }

    public void ToString() {
        System.out.println("# OF CARS : "+list.size());
        for(Car car : list) {
            car.ToString();
        }
        System.out.println("=================");
    }

}
