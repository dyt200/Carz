package com.example.carz.Database.Repository;

import androidx.lifecycle.LiveData;

import com.example.carz.Database.Entities.Car;
import com.example.carz.Database.Entities.CarSearchParameters;
import com.example.carz.Database.Firebase.CarListLiveData;
import com.example.carz.Database.Firebase.CarLiveData;
import com.example.carz.Util.OnAsyncEventListener;
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

    public void insert(final Car car, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(car.getUser());
        String key = reference.push().getKey();
        car.setId(key);
        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(key)
                .setValue(car, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<Car>> getAllCars() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");
        return new CarListLiveData(reference, "", false, false, null);
    }

    public LiveData<List<Car>> getSearchResults(CarSearchParameters carSearchParameters) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");
        return new CarListLiveData(reference, "", false, true, carSearchParameters);
    }

    public LiveData<List<Car>> getAllOtherCars(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");
        return new CarListLiveData(reference, userId, false, false, null);
    }

    public LiveData<List<Car>> getMyCars(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars")
                .child("");
        return new CarListLiveData(reference, userId, true, false, null);
    }

    public LiveData<Car> getCarById(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(id);
        return new CarLiveData(reference);
    }

    public void update(final Car car, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(car.getId())
                .updateChildren(car.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
    public void delete(final Car car, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(car.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
