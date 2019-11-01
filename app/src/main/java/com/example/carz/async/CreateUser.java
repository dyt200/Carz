package com.example.carz.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.User;
import com.example.carz.util.OnAsyncEventListener;

public class CreateUser extends AsyncTask<User, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateUser(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(User... params) {
        try {
            for (User user : params)
                database.userDao().insert(user);
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
