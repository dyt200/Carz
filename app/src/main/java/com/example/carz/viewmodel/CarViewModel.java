package com.example.carz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.Entities.Car;
import com.example.carz.pojo.CarWithImages;
import com.example.carz.repositories.CarRepository;
import com.example.carz.util.OnAsyncEventListener;
import com.example.carz.util.OnAsyncInsertEventListener;

import org.jetbrains.annotations.NotNull;

public class CarViewModel extends AndroidViewModel {

    private CarRepository repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<CarWithImages> observableCar;

    public CarViewModel(
            @NonNull Application application,
            final int carId,
            CarRepository carRepository
    ) {
        super(application);
        this.application = application;
        repository = carRepository;
        observableCar = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableCar.setValue(null);
        LiveData<CarWithImages> car = repository.getCarById(carId, application);

        // observe the changes of the client entity from the database and forward them
        observableCar.addSource(car, observableCar::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int carId;

        private final CarRepository repository;

        public Factory(@NonNull Application application, int carId) {
            this.application = application;
            this.carId = carId;
            repository = CarRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarViewModel(application, carId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<CarWithImages> getCar() {
        return observableCar;
    }

    public void createCar(Car car, OnAsyncInsertEventListener callback) {
        repository.insert(car, callback, application);
    }

    public void updateCar(Car car, OnAsyncEventListener callback) {
        repository.update(car, callback, application);
    }

    public void deleteCar(Car car, OnAsyncEventListener callback) {
        repository.delete(car, callback, application);

    }
}
