package com.example.carz.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.Entities.CarImage;
import com.example.carz.repositories.ImageRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageListViewModel extends AndroidViewModel {
    private ImageRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<CarImage>> observableImages;

    public ImageListViewModel(int carId, @NonNull Application application, ImageRepository clientRepository) {
        super(application);

        repository = clientRepository;

        applicationContext = application.getApplicationContext();

        observableImages = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableImages.setValue(null);

        LiveData<List<CarImage>> clients = repository.getImagesForCar(carId, applicationContext);

        // observe the changes of the entities from the database and forward them
        observableImages.addSource(clients, observableImages::setValue);
    }

    /**
     * A creator is used to inject the car id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private int carId;
        private final ImageRepository userRepository;

        public Factory(int carId, @NonNull Application application) {
            this.application = application;
            this.carId = carId;
            userRepository = ImageRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ImageListViewModel(carId, application, userRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<CarImage>> getImages() {
        return observableImages;
    }
}
