package com.example.carz.Database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.carz.Entities.Car;
import com.example.carz.Entities.CarImage;
import com.example.carz.Entities.User;

class DatabaseInitializer {

    private static final String TAG = "DatabaseInitializer";

    static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data...");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addImage(
            final AppDatabase db,
            final String url,
            final int car
    ) {
        CarImage image = new CarImage(
                car,
                url
        );
        db.imageDao().insert(image);
    }

    private static void addCar(
            final AppDatabase db,
            final int type,
            final int manufacturer,
            final String user,
            final int price,
            final int year,
            final int mileage,
            final String model,
            final String description,
            final String condition
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
                condition
        );
        db.carDao().insert(car);
    }

    private static void addUser(
            final AppDatabase db,
            final String id,
            final String firstName,
            final String lastName,
            final String email,
            final String password,
            final String telephone,
            final String address
    ) {
        User user = new User(
                id,
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
        db.userDao().deleteAll();
        addUser( db, "a","Ben", "Pocklington", "a", "a", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");
        addUser( db, "b","Dylan", "Thompson", "dylan@test.com", "test","0791234567", "Route de Test 16, Ayent, Valais, Suisse");
        addUser( db, "c","Cloud", "Strife", "cloud@test.com", "test","0791234567", "No. 1, Sector 7, Midgar");
      //  addUser( db, "d","Coline", "Van Dorpe", "coline@test.com", "a", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");
       // addUser( db,"c","Micheal", "Schumacher", "djschoom@test.com", "b", "0791234567", "Route de Test 16, Ayent, Valais, Suisse");

        db.carDao().deleteAll();
        addCar(db, 1, 2, "a",15000, 2015, 85000,  "M4","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.");
        addCar(db, 2, 2, "b",10000,2008, 126500,  "Octavia","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 2, 2, "c",12500,1999, 250000,  "6 Series","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
      //  addCar(db, 1, 3, "d",25000,2001, 180000,  "Octavia","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
/*        addCar(db, 1, 1, 5,30000,2010, 95000,  "Octavia","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 3, 1, 2,15000,2015, 85000,  "Super Sport","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 1, 2, 4,15000,2008, 126500,  "6 Series","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 3, 2, 5,15000,1999, 250000,  "6 Series","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 2, 3, 5,25000,2001, 180000, "6 Series","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 2, 1, 2,15000,2010, 95000,  "Octavia","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 1, 1, 2,10000,2015, 85000,  "Super Sport","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 1, 2, 3,12000,2008, 126500,  "Super Sport","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 3, 2, 4,11000,1999, 250000,  "Super Sport","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 2, 3, 3,13500,2001, 180000,  "6 Series","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");
        addCar(db, 2, 1, 1,15000,2010, 95000,  "6 Series","This is my old car that I used every day to go to the HES where I learned lots of nice things. It means a lot to me so I hope it goes to a good home! Thank you for your interest!","Tires need changing and the brakes don't work. There is also a large white stain on the seat, sorry.\"");*/

        db.imageDao().deleteAll();
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fbmw.jpg?alt=media&token=c7fc147e-5088-4a3e-8654-f8861d283b07",1);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fbmw_2.jpg?alt=media&token=053ea660-ef9e-42ed-82df-be14334cf0b8",1);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fbmw_3.png?alt=media&token=2ca0cb91-d471-480f-8450-dc4e65f33a74",1);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fbmw.jpg?alt=media&token=c7fc147e-5088-4a3e-8654-f8861d283b07",2);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fopel.jpg?alt=media&token=00dae790-d18f-4762-97e1-eb142ba6e5af",3);
/*        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fford.jpg?alt=media&token=91b2f821-df6f-48f8-8641-f80878b4b748",4);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fskoda.jpeg?alt=media&token=fdc0ddad-666c-4870-b51c-0a1f05b2f6bd",5);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fskoda.jpeg?alt=media&token=fdc0ddad-666c-4870-b51c-0a1f05b2f6bd",6);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fbmw.jpg?alt=media&token=c7fc147e-5088-4a3e-8654-f8861d283b07",7);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fopel.jpg?alt=media&token=00dae790-d18f-4762-97e1-eb142ba6e5af",8);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fford.jpg?alt=media&token=91b2f821-df6f-48f8-8641-f80878b4b748",9);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fskoda.jpeg?alt=media&token=fdc0ddad-666c-4870-b51c-0a1f05b2f6bd",10);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fskoda.jpeg?alt=media&token=fdc0ddad-666c-4870-b51c-0a1f05b2f6bd",11);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fbmw.jpg?alt=media&token=c7fc147e-5088-4a3e-8654-f8861d283b07",12);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fopel.jpg?alt=media&token=00dae790-d18f-4762-97e1-eb142ba6e5af",13);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fford.jpg?alt=media&token=91b2f821-df6f-48f8-8641-f80878b4b748",14);
        addImage(db, "https://firebasestorage.googleapis.com/v0/b/carz-c49b0.appspot.com/o/demoData%2Fskoda.jpeg?alt=media&token=fdc0ddad-666c-4870-b51c-0a1f05b2f6bd",15);*/
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
