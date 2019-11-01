package com.example.carz.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.carz.Async.CreateUser;
import com.example.carz.Async.DeleteUser;
import com.example.carz.Async.UpdateUser;
import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.User;
import com.example.carz.Util.OnAsyncEventListener;

import java.util.List;

public class UserRepository {
    private static UserRepository instance;

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<User>> getAllUsers(Context context) {
        return AppDatabase.getInstance(context).userDao().getAll();
    }

    public void insert(final User user, OnAsyncEventListener callback, Context context) {
        new CreateUser(context, callback).execute(user);
    }

    public void update(final User user, OnAsyncEventListener callback, Context context) {
        new UpdateUser(context, callback).execute(user);
    }

    public void delete(final User user, OnAsyncEventListener callback, Context context) {
        new DeleteUser(context, callback).execute(user);
    }
}
