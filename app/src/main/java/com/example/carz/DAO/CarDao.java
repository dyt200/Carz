package com.example.carz.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.carz.Entities.Car;
import java.util.List;

@Dao
public interface CarDao {
    @Query("SELECT * FROM car")
    LiveData<List<Car>> getAll();

    @Query("SELECT * FROM car WHERE id = (:id)")
    LiveData<Car> getCarById(int id);

    @Query("SELECT * FROM car WHERE user = (:userId)")
    LiveData<List<Car>> getMyCars(int userId);

    @RawQuery(observedEntities = Car.class)
    LiveData<List<Car>> getSearchResults(SimpleSQLiteQuery query);

    @Insert
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

    @Query("DELETE FROM car")
    void deleteAll();

}
