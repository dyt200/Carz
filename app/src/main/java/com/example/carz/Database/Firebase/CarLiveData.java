package com.example.carz.Database.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.carz.Database.Entities.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class CarLiveData extends LiveData<Car> {
    private static final String TAG = "ClientLiveData";

    private final DatabaseReference reference;
    private final CarLiveData.MyValueEventListener listener = new CarLiveData.MyValueEventListener();

    public CarLiveData(DatabaseReference ref) {
        this.reference = ref;
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
            Car entity = dataSnapshot.getValue(Car.class);
            assert entity != null;
            entity.setId(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
