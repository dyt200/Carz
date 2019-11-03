package com.example.carz.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.Car;
import com.example.carz.util.OnAsyncEventListener;

public class UpdateCar extends AsyncTask<Car, Void, Void> {
    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateCar(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Car... params) {
        try {
            for (Car car : params)
                database.carDao().update(car);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
