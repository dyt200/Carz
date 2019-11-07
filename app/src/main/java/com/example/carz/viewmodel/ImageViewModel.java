package com.example.carz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.Entities.CarImage;
import com.example.carz.repositories.ImageRepository;
import com.example.carz.util.OnAsyncEventListener;

import org.jetbrains.annotations.NotNull;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<CarImage> observableImage;

    private ImageViewModel(
            int imageId,
            @NonNull Application application,
            ImageRepository imageRepository
    ) {
        super(application);

        this.application = application;
        repository = imageRepository;
        observableImage = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableImage.setValue(null);
        LiveData<CarImage> image = repository.getImageById(imageId, application);

        // observe the changes of the client entity from the database and forward them
        observableImage.addSource(image, observableImage::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final int imageId;
        private final ImageRepository repository;

        public Factory(@NonNull Application application, int imageId){
            this.application = application;
            this.imageId = imageId;
            repository = ImageRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ImageViewModel(imageId,application, repository);
        }
    }



    /**
     * Expose the LiveData User query so the UI can observe it.
     */
    public LiveData<CarImage> getImage() {
        return observableImage;
    }

    public void createImage(CarImage image, OnAsyncEventListener callback) {
        repository.insert(image, callback, application);
    }

    public void updateImage(CarImage image, OnAsyncEventListener callback) {
        repository.update(image, callback, application);
    }

    public void deleteImage(CarImage image, OnAsyncEventListener callback) {
        repository.delete(image, callback, application);

    }
}
