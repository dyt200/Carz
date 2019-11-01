package com.example.carz;

import android.app.Application;

import com.example.carz.Database.AppDatabase;
import com.example.carz.repositories.CarRepository;
import com.example.carz.repositories.UserRepository;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public CarRepository getCarRepository() {
        return CarRepository.getInstance();
    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }
}
