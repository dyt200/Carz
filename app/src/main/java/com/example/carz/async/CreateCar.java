package com.example.carz.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.Car;
import com.example.carz.util.OnAsyncEventListener;
import com.example.carz.util.OnAsyncInsertEventListener;

public class CreateCar extends AsyncTask<Car, Void, Long> {
    private AppDatabase database;
    private OnAsyncInsertEventListener callback;
    private Exception exception;

    public CreateCar(Context context, OnAsyncInsertEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }
    @Override
    protected Long doInBackground(Car... params) {
        try {
            for (Car car : params)
                 return database.carDao().insert(car);

        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long id) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccessResult(id);
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
