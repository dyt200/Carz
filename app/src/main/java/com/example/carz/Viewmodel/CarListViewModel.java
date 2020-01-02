package com.example.carz.Viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.Database.Entities.Car;
import com.example.carz.Database.Entities.CarSearchParameters;
import com.example.carz.Database.Repository.CarRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CarListViewModel extends AndroidViewModel {
    private CarRepo repository;

    private Context applicationContext;

    private final MediatorLiveData<List<Car>> observableCars;

    private CarListViewModel(@NonNull Application application, CarRepo carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<Car>> cars = repository.getAllCars();
        observableCars.addSource(cars, observableCars::setValue);
    }

   private CarListViewModel(String userId, boolean check, @NonNull Application application, CarRepo carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<Car>> cars = repository.getAllOtherCars(userId);
        observableCars.addSource(cars, observableCars::setValue);
    }


    private CarListViewModel(@NonNull String userId, Application application, CarRepo carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<Car>> cars = repository.getMyCars(userId);
        observableCars.addSource(cars, observableCars::setValue);
    }


    private CarListViewModel(@NonNull CarSearchParameters searchParameters, Application application, CarRepo carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<Car>> cars = repository.getSearchResults(searchParameters);
        observableCars.addSource(cars, observableCars::setValue);
    }

    public static class AllCarsFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final CarRepo carRepository;

        public AllCarsFactory(@NonNull Application application) {
            this.application = application;
            carRepository = CarRepo.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListViewModel(application, carRepository);
        }
    }

   public static class AllOtherCarsFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final CarRepo carRepository;
        private final String userId;
        private final boolean check;

        public AllOtherCarsFactory(String userId, boolean check, @NonNull Application application) {
            this.application = application;
            this.userId = userId;
            this.check = check;
            carRepository = CarRepo.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListViewModel(userId, check, application, carRepository);
        }
    }


    public static class MyCarsFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String userId;
        private final CarRepo carRepository;

        public MyCarsFactory(String userId, @NonNull Application application) {
            this.application = application;
            this.userId = userId;
            carRepository = CarRepo.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListViewModel(userId, application, carRepository);
        }
    }


    public static class SearchCarFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final CarRepo carRepository;
        private final CarSearchParameters searchParameters;

        public SearchCarFactory(CarSearchParameters searchParameters, @NonNull Application application) {
            this.application = application;
            this.searchParameters = searchParameters;
            carRepository = CarRepo.getInstance();
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
