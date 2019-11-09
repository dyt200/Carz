package com.example.carz.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.carz.pojo.CarWithImages;
import com.example.carz.async.CreateCar;
import com.example.carz.async.DeleteCar;
import com.example.carz.async.UpdateCar;
import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.Car;
import com.example.carz.util.OnAsyncEventListener;
import com.example.carz.util.OnAsyncInsertEventListener;

import java.util.List;

public class CarRepository {
    private static CarRepository instance;

    private CarRepository() {}

    public static CarRepository getInstance() {
        //singleton
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new CarRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<CarWithImages>> getAllCars(Context context) {
        return AppDatabase.getInstance(context).carDao().getAll();
    }

    public LiveData<CarWithImages> getCarById(int id, Context context) {
        return AppDatabase.getInstance(context).carDao().getCarById(id);
    }

    public LiveData<List<CarWithImages>> getMyCars(int userId, Context context) {
        return AppDatabase.getInstance(context).carDao().getMyCars(userId);
    }

    public LiveData<List<CarWithImages>> getSearchResults(SimpleSQLiteQuery query, Context context) {
        return AppDatabase.getInstance(context).carDao().getSearchResults(query);
    }

    public void insert(final Car car, OnAsyncInsertEventListener callback, Context context) {
        new CreateCar(context, callback).execute(car);
    }

    public void update(final Car car, OnAsyncEventListener callback, Context context) {
        new UpdateCar(context, callback).execute(car);
    }

    public void delete(final Car car, OnAsyncEventListener callback, Context context) {
        new DeleteCar(context, callback).execute(car);
    }

}
