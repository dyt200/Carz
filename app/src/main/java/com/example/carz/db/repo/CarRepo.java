package com.example.carz.db.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.carz.Entities.User;
import com.example.carz.db.entities.FCar;
import com.example.carz.db.firebase.CarListLiveData;
import com.example.carz.db.firebase.CarLiveData;
import com.example.carz.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
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

    public LiveData<List<CarWithImages>> getMyCars(String userId, Context context) {
        return AppDatabase.getInstance(context).carDao().getMyCars(userId);
    }

    public LiveData<List<CarWithImages>> getSearchResults(SimpleSQLiteQuery query, Context context) {
        return AppDatabase.getInstance(context).carDao().getSearchResults(query);
    }*/

    public void insert(final FCar car, final OnAsyncEventListener callback) {
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

    public LiveData<List<FCar>> getAllCars() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");
        return new CarListLiveData(reference, "", false);
    }

    public LiveData<List<FCar>> getAllOtherCars(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");
        return new CarListLiveData(reference, userId, false);
    }

    public LiveData<List<FCar>> getMyCars(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars")
                .child("");
        return new CarListLiveData(reference, userId, true);
    }




    public LiveData<FCar> getCarById(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(id);
        return new CarLiveData(reference);
    }

    public void update(final FCar car, final OnAsyncEventListener callback) {
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
/*        FirebaseAuth.getInstance().getCurrentUser().updatePassword(user.getPassword())
                .addOnFailureListener(
                        e -> Log.d(TAG, "updatePassword failure!", e)
                );*/
    }
    public void delete(final FCar car, OnAsyncEventListener callback) {
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

/*    public void insert(final FCar car, OnAsyncInsertEventListener callback, Context context) {
        new CreateCar(context, callback).execute(car);
    }*/

/*    public void update(final FCar car, OnAsyncEventListener callback, Context context) {
        new UpdateCar(context, callback).execute(car);
    }

    public void delete(final FCar car, OnAsyncEventListener callback, Context context) {
        new DeleteCar(context, callback).execute(car);
    }*/

}
