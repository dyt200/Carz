package com.example.carz.db.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.Car;
import com.example.carz.async.CreateCar;
import com.example.carz.async.DeleteCar;
import com.example.carz.async.UpdateCar;
import com.example.carz.pojo.CarWithImages;
import com.example.carz.repositories.CarRepository;
import com.example.carz.util.OnAsyncEventListener;
import com.example.carz.util.OnAsyncInsertEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CarRepo {
    private static CarRepo instance;

    private CarRepo() {}

    public static CarRepo getInstance() {
        //singleton
        if (instance == null) {
            synchronized (CarRepo.class) {
                if (instance == null) {
                    instance = new CarRepo();
                }
            }
        }
        return instance;
    }

/*    public LiveData<List<CarWithImages>> getAllCars(Context context) {
        return AppDatabase.getInstance(context).carDao().getAll();
    }

    public LiveData<List<CarWithImages>> getAllOtherCars(String userId, Context context) {
        return AppDatabase.getInstance(context).carDao().getAllOther(userId);
    }

    public LiveData<CarWithImages> getCarById(int id, Context context) {
        return AppDatabase.getInstance(context).carDao().getCarById(id);
    }

    public LiveData<List<CarWithImages>> getMyCars(String userId, Context context) {
        return AppDatabase.getInstance(context).carDao().getMyCars(userId);
    }

    public LiveData<List<CarWithImages>> getSearchResults(SimpleSQLiteQuery query, Context context) {
        return AppDatabase.getInstance(context).carDao().getSearchResults(query);
    }*/

    public void insert(final Car car, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(car.getUser());
        String key = reference.push().getKey();
        car.setId(key);
        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(car.getUser())
                .child(key)
                .setValue(car, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

/*    public void insertImages(final Car car, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(car.getUser())
                .child(ca)
                .setValue(car, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }*/

/*    public void insert(final Car car, OnAsyncInsertEventListener callback, Context context) {
        new CreateCar(context, callback).execute(car);
    }*/

/*    public void update(final Car car, OnAsyncEventListener callback, Context context) {
        new UpdateCar(context, callback).execute(car);
    }

    public void delete(final Car car, OnAsyncEventListener callback, Context context) {
        new DeleteCar(context, callback).execute(car);
    }*/

}
