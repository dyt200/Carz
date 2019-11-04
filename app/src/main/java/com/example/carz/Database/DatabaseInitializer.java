package com.example.carz.Database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.carz.Entities.Car;
import com.example.carz.Entities.User;

class DatabaseInitializer {

    private static final String TAG = "DatabaseInitializer";

    static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data...");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addCar(
            final AppDatabase db,
            final int type,
            final int manufacturer,
            final int user,
            final int price,
            final int year,
            final int mileage,
            final String model,
            final String description,
            final String condition,
            final String image1,
            final String image2
    ) {
        Car car = new Car(
                type,
                manufacturer,
                user,
                price,
                year,
                mileage,
                model,
                description,
                condition,
                image1,
                image2
        );
        db.carDao().insert(car);
    }

    private static void addUser(
            final AppDatabase db,
            final String firstName,
            final String lastName,
            final String email,
            final String password,
            final String telephone,
            final String address
    ) {
        User user = new User(
                firstName,
                lastName,
                email,
                password,
                telephone,
                address
        );
        db.userDao().insert(user);
    }

    /**
     * Insert test data into the database
     * @param db the database
     */
    private static void populateWithTestData(AppDatabase db) {
        db.carDao().deleteAll();
        addCar(db, 1, 1, 1,15000, 2015, 85000,  "6 Series","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1","");
        addCar(db, 2, 2, 2,10000,2008, 126500,  "Octavia","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_2","");
        addCar(db, 2, 2, 3,12500,1999, 250000,  "6 Series","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_3","");
        addCar(db, 1, 3, 4,25000,2001, 180000,  "Octavia","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_4","");
        addCar(db, 1, 1, 5,30000,2010, 95000,  "Octavia","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1","");
        addCar(db, 3, 1, 2,15000,2015, 85000,  "Super Sport","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.","","car_example_1","");
        addCar(db, 1, 2, 4,15000,2008, 126500,  "6 Series","","","car_example_2","");
        addCar(db, 3, 2, 5,15000,1999, 250000,  "6 Series","","","car_example_3","");
        addCar(db, 2, 3, 5,25000,2001, 180000, "6 Series","","","car_example_4","");
        addCar(db, 2, 1, 2,15000,2010, 95000,  "Octavia","","","car_example_1","");
        addCar(db, 1, 1, 2,10000,2015, 85000,  "Super Sport","","","car_example_1","");
        addCar(db, 1, 2, 3,12000,2008, 126500,  "Super Sport","","","car_example_2","");
        addCar(db, 3, 2, 4,11000,1999, 250000,  "Super Sport","","","car_example_3","");
        addCar(db, 2, 3, 3,13500,2001, 180000,  "6 Series","","","car_example_4","");
        addCar(db, 2, 1, 1,15000,2010, 95000,  "6 Series","","","car_example_1","");

        db.userDao().deleteAll();
        addUser( db,"Ben", "Pocklington", "ben@test.com", "test", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");
        addUser( db,"Dylan", "Thompson", "dylan@test.com", "test","0791234567", "Route de Test 16, Ayent, Valais, Suisse");
        addUser( db,"Cloud", "Strife", "cloud@test.com", "test","0791234567", "No. 1, Sector 7, Midgar");
        addUser( db,"test", "test", "a", "a", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }
    }
}
