package com.example.carz.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.carz.async.CreateCar;
import com.example.carz.async.DeleteCar;
import com.example.carz.async.UpdateCar;
import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.Car;
import com.example.carz.util.OnAsyncEventListener;

import java.util.List;

public class CarRepository {
    private static CarRepository instance;

    private CarRepository() {}

    public static CarRepository getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new CarRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Car>> getAllCars(Context context) {
        return AppDatabase.getInstance(context).carDao().getAll();
    }

    public LiveData<Car> getCarById(int id, Context context) {
        return AppDatabase.getInstance(context).carDao().getCarById(id);
    }

    public void insert(final Car car, OnAsyncEventListener callback, Context context) {
        new CreateCar(context, callback).execute(car);
    }

    public void update(final Car car, OnAsyncEventListener callback, Context context) {
        new UpdateCar(context, callback).execute(car);
    }

    public void delete(final Car car, OnAsyncEventListener callback, Context context) {
        new DeleteCar(context, callback).execute(car);
    }

}
