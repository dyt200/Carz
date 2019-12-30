package com.example.carz.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.carz.Database.AppDatabase;
import com.example.carz.Entities.CarImage;
import com.example.carz.Entities.User;
import com.example.carz.async.CreateImage;
import com.example.carz.async.DeleteImage;
import com.example.carz.async.UpdateImage;
import com.example.carz.util.OnAsyncEventListener;

import java.util.List;

public class ImageRepository {
    private static ImageRepository instance;

    private ImageRepository() {}

    public static ImageRepository getInstance() {
        //singleton
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new ImageRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CarImage> getImageById(int imageId, Context context) {
        return AppDatabase.getInstance(context).imageDao().getImageById(imageId);
    }

    public LiveData<List<CarImage>> getImagesForCar(int carId, Context context) {
        return AppDatabase.getInstance(context).imageDao().getImagesForCar(carId);
    }

    public void insert(final CarImage image, OnAsyncEventListener callback, Context context) {
        new CreateImage(context, callback).execute(image);
    }

    public void update(final CarImage image, OnAsyncEventListener callback, Context context) {
        new UpdateImage(context, callback).execute(image);
    }

/*    public void delete(final CarImage image, OnAsyncEventListener callback, Context context) {
        new DeleteImage(context, callback).execute(image);
    }*/
}
