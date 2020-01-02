package com.example.carz.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.carz.Entities.CarSearchParameters;
import com.example.carz.db.entities.FCar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarListLiveData extends LiveData<List<FCar>> {


    private static final String TAG = "CarListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final Boolean isUserCars;
    private final Boolean isSearch;
    private final CarSearchParameters carSearchParameters;


/*    private final String owner;*/
    private final MyValueEventListener listener = new MyValueEventListener();

    public CarListLiveData(DatabaseReference ref, String userId, Boolean myCars, Boolean search, CarSearchParameters searchParams) {
        reference = ref;
        owner = userId;
        isUserCars = myCars;
        isSearch = search;
        carSearchParameters = searchParams;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (isSearch) {
                setValue(toSearchCars(dataSnapshot));
            }
            else if (owner.equals("")){
                setValue(toCars(dataSnapshot));
            }else if (!isUserCars)  {
                setValue(toCarsNoOwner(dataSnapshot));
            }
            else {
                setValue(toMyCars(dataSnapshot));
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<FCar> toSearchCars(DataSnapshot snapshot) {
        List<FCar> cars = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            FCar entity = childSnapshot.getValue(FCar.class);
            entity.setId(childSnapshot.getKey());
            if (entity.getType() == carSearchParameters.getType()){
                cars.add(entity);
            }
            else if (entity.getManufacturer() == carSearchParameters.getManufacturer()){
                cars.add(entity);
            }
            else if (entity.getMileage() >= carSearchParameters.getMinMileage() && carSearchParameters.getMinMileage() > 0 ){
                cars.add(entity);
            }
            else if (entity.getMileage() <= carSearchParameters.getMaxMileage() && carSearchParameters.getMaxMileage() > 0){
                cars.add(entity);
            }
            else if (entity.getYear() >= carSearchParameters.getMinYear() && carSearchParameters.getMinYear() > 0){
                cars.add(entity);
            }
            else if (entity.getYear() <= carSearchParameters.getMaxYear() && carSearchParameters.getMaxYear() > 0){
                cars.add(entity);
            }
            else if (entity.getModel().equals(carSearchParameters.getModel())){
                cars.add(entity);
            }
        }
        return cars;
    }

    private List<FCar> toCars(DataSnapshot snapshot) {
        List<FCar> cars = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            FCar entity = childSnapshot.getValue(FCar.class);
            entity.setId(childSnapshot.getKey());
            cars.add(entity);
        }
        return cars;
    }

    private List<FCar> toCarsNoOwner(DataSnapshot snapshot) {
        List<FCar> cars = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            FCar entity = childSnapshot.getValue(FCar.class);
            entity.setId(childSnapshot.getKey());
            if (!entity.getUser().equals(owner)){
                cars.add(entity);
            }
        }
        return cars;
    }

    private List<FCar> toMyCars(DataSnapshot snapshot) {
        List<FCar> cars = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            FCar entity = childSnapshot.getValue(FCar.class);
            entity.setId(childSnapshot.getKey());
            if (entity.getUser().equals(owner)){
                cars.add(entity);
            }
        }
        return cars;
    }
}
