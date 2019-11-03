package com.example.carz.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.Entities.Car;
import com.example.carz.repositories.CarRepository;


import java.util.List;

public class CarListViewModel extends AndroidViewModel {
    private CarRepository repository;

    private Context applicationContext;

    private final MediatorLiveData<List<Car>> observableCars;

    public CarListViewModel(@NonNull Application application, CarRepository carRepository){
        super(application);

        repository = carRepository;

        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<Car>> cars = repository.getAllCars(application);

        observableCars.addSource(cars, observableCars::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final CarRepository carRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            carRepository = CarRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListViewModel(application, carRepository);
        }
    }

    public LiveData<List<Car>> getCars() {
        return observableCars;
    }

}
