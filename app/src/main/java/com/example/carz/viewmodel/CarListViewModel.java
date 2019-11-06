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
import com.example.carz.Entities.CarSearchParameters;
import com.example.carz.repositories.CarRepository;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CarListViewModel extends AndroidViewModel {
    private CarRepository repository;

    private Context applicationContext;

    private final MediatorLiveData<List<Car>> observableCars;

    private CarListViewModel(@NonNull Application application, CarRepository carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<Car>> cars = repository.getAllCars(application);
        observableCars.addSource(cars, observableCars::setValue);
    }

    private CarListViewModel(@NonNull int userId, Application application, CarRepository carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<Car>> cars = repository.getMyCars(userId, application);
        observableCars.addSource(cars, observableCars::setValue);
    }

    private CarListViewModel(@NonNull CarSearchParameters searchParameters, Application application, CarRepository carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<Car>> cars = repository.getSearchResults(searchParameters.getDatabaseQuery(), application);
        observableCars.addSource(cars, observableCars::setValue);
    }

    public static class AllCarsFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final CarRepository carRepository;

        public AllCarsFactory(@NonNull Application application) {
            this.application = application;
            carRepository = CarRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListViewModel(application, carRepository);
        }
    }

    public static class MyCarsFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final int userId;
        private final CarRepository carRepository;

        public MyCarsFactory(int userId, @NonNull Application application) {
            this.application = application;
            this.userId = userId;
            carRepository = CarRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListViewModel(userId, application, carRepository);
        }
    }

    public static class CarSearchFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final CarRepository carRepository;
        private final CarSearchParameters searchParameters;

        public CarSearchFactory(CarSearchParameters searchParameters, @NonNull Application application) {
            this.application = application;
            this.searchParameters = searchParameters;
            carRepository = CarRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListViewModel(searchParameters, application, carRepository);
        }
    }

    public LiveData<List<Car>> getCars() {
        return observableCars;
    }

}
