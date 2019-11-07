package com.example.carz.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.carz.Entities.Car;
import com.example.carz.Entities.CarImage;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM image WHERE id = (:id)")
    LiveData<CarImage> getImageById(int id);

    @Query("SELECT * FROM image WHERE car = (:carId)")
    LiveData<List<CarImage>> getImagesForCar(int carId);

    @Insert
    void insert(CarImage image);

    @Update
    void update(CarImage image);

    @Delete
    void delete(CarImage image);

    @Query("DELETE FROM image")
    void deleteAll();
}
