package com.example.carz.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.Entities.CarSearchParameters;
import com.example.carz.db.entities.FCar;
import com.example.carz.db.repo.CarRepo;
import com.example.carz.pojo.CarWithImages;
import com.example.carz.repositories.CarRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FCarListViewModel extends AndroidViewModel {
    private CarRepo repository;

    private Context applicationContext;

    private final MediatorLiveData<List<FCar>> observableCars;

    private FCarListViewModel(@NonNull Application application, CarRepo carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<FCar>> cars = repository.getAllCars();
        observableCars.addSource(cars, observableCars::setValue);
    }

   private FCarListViewModel(String userId, boolean check, @NonNull Application application, CarRepo carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<FCar>> cars = repository.getAllOtherCars(userId);
        observableCars.addSource(cars, observableCars::setValue);
    }


    private FCarListViewModel(@NonNull String userId, Application application, CarRepo carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<FCar>> cars = repository.getMyCars(userId);
        observableCars.addSource(cars, observableCars::setValue);
    }


    /*
    private FCarListViewModel(@NonNull CarSearchParameters searchParameters, Application application, CarRepository carRepository){
        super(application);
        repository = carRepository;
        applicationContext = application.getApplicationContext();

        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<CarWithImages>> cars = repository.getSearchResults(searchParameters.getDatabaseQuery(), application);
        observableCars.addSource(cars, observableCars::setValue);
    }*/

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
            return (T) new FCarListViewModel(application, carRepository);
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
            return (T) new FCarListViewModel(userId, check, application, carRepository);
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
            return (T) new FCarListViewModel(userId, application, carRepository);
        }
    }

    /*

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
            return (T) new FCarListViewModel(searchParameters, application, carRepository);
        }
    }*/

    public LiveData<List<FCar>> getCars() {
        return observableCars;
    }

}
