package com.example.carz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.db.entities.FCar;
import com.example.carz.db.repo.CarRepo;
import com.example.carz.util.OnAsyncEventListener;


import org.jetbrains.annotations.NotNull;

public class FCarViewModel extends AndroidViewModel {

    private CarRepo repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<FCar> observableCar;

    public FCarViewModel(
            @NonNull Application application,
            final String carId,
            CarRepo carRepository
    ) {
        super(application);
        this.application = application;
        repository = carRepository;
        observableCar = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableCar.setValue(null);
        LiveData<FCar> car = repository.getCarById(carId);

        // observe the changes of the client entity from the database and forward them
        observableCar.addSource(car, observableCar::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String carId;

        private final CarRepo repository;

        public Factory(@NonNull Application application, String carId) {
            this.application = application;
            this.carId = carId;
            repository = CarRepo.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new FCarViewModel(application, carId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<FCar> getCar() {
        return observableCar;
    }


   public void updateCar(FCar car, OnAsyncEventListener callback) {
       repository.update(car, callback);
    }
    public void deleteCar(FCar car, OnAsyncEventListener callback) {
        repository.delete(car, callback);

    }
}
