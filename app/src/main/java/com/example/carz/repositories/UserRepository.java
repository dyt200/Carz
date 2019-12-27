package com.example.carz.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.carz.async.CreateUser;
import com.example.carz.async.DeleteUser;
import com.example.carz.async.UpdateUser;
import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.User;
import com.example.carz.util.OnAsyncEventListener;

import java.util.List;

public class UserRepository {
    private static UserRepository instance;

    private UserRepository() {}

    public static UserRepository getInstance() {
        //singleton
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

/*    public LiveData<User> getUserById(String id, Context context) {
        return AppDatabase.getInstance(context).userDao().getUserById(id);
    }*/

/*    public LiveData<User> validateLogin(String email, String pass, Context context) {
        return AppDatabase.getInstance(context).userDao().validateLogin(email, pass);
    }

    public LiveData<User> doesEmailExist(String email, Context context) {
        return AppDatabase.getInstance(context).userDao().doesEmailExist(email);
    }*/

/*    public void insert(final User user, OnAsyncEventListener callback, Context context) {
        new CreateUser(context, callback).execute(user);
    }*/

/*    public void update(final User user, OnAsyncEventListener callback, Context context) {
        new UpdateUser(context, callback).execute(user);
    }

    public void delete(final User user, OnAsyncEventListener callback, Context context) {
        new DeleteUser(context, callback).execute(user);
    }*/
}
