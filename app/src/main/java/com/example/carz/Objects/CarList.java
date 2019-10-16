package com.example.carz.Objects;

import java.util.ArrayList;

public class CarList {
    public ArrayList<Car> list;

    public CarList() {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add( new Car(1, 1, 1, 1,2015, 85000, "BMW 6 Series", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1",""));
        cars.add( new Car(2, 2, 2, 1,2008, 126500, "Skoda Octavia 2008 TDI", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_2",""));
        cars.add( new Car(3, 2, 2, 1,1999, 250000, "Skoda Octavia", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_3",""));
        cars.add( new Car(4, 1, 3, 1,2001, 180000, "Opal Corsa 1.2L", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_4",""));
        cars.add( new Car(5, 1, 1, 1,2010, 95000, "BMW Coupé 2010", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1",""));
        cars.add( new Car(6, 3, 1, 1,2015, 85000, "BMW 6 Series", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1",""));
        cars.add( new Car(7, 1, 2, 1,2008, 126500, "Skoda Octavia 2008 TDI", "","","car_example_2",""));
        cars.add( new Car(8, 3, 2, 1,1999, 250000, "Skoda Octavia", "","","car_example_3",""));
        cars.add( new Car(9, 2, 3, 1,2001, 180000, "Opal Corsa 1.2L", "","","car_example_4",""));
        cars.add( new Car(10, 2, 1, 1,2010, 95000, "BMW Coupé 2010", "","","car_example_1",""));
        cars.add( new Car(11, 1, 1, 1,2015, 85000, "BMW 6 Series", "","","car_example_1",""));
        cars.add( new Car(12, 1, 2, 1,2008, 126500, "Skoda Octavia 2008 TDI", "","","car_example_2",""));
        cars.add( new Car(13, 3, 2, 1,1999, 250000, "Skoda Octavia", "","","car_example_3",""));
        cars.add( new Car(14, 2, 3, 1,2001, 180000, "Opal Corsa 1.2L", "","","car_example_4",""));
        cars.add( new Car(15, 2, 1, 1,2010, 95000, "BMW Coupé 2010", "","","car_example_1",""));
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
        if(type > 0) {
            int count = 0;
            while (list.size() > count) {
                if (list.get(count).getType() != type) {
                    list.remove(count);
                }
                count++;
            }

        }
    }

    private void filterManufacturer(int type) {
        if(type > 0) {
            int count = 0;
            while (list.size() > count) {
                if (list.get(count).getManufacturer() != type) {
                    list.remove(count);
                }
                count++;
            }
        }
    }

    private void filterMileage(int min, int max) {
        int count = 0;
        int mileage;
        while (list.size() > count) {
            mileage = list.get(count).getMileage();
            if(max > 0 && mileage > max) {
                list.remove(count);
            }
            if(min > 0 && mileage < min) {
                list.remove(count);
            }
            count++;
        }
    }
    private void filterYear(int min, int max) {
        int count = 0;
        int year;
        while (list.size() > count) {
            year = list.get(count).getYear();
            if(max > 0 && year > max) {
                list.remove(count);
            }
            if(min > 0 && year < min) {
                list.remove(count);
            }
            count++;
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
