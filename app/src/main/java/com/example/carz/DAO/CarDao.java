package com.example.carz.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carz.Entities.Car;
import java.util.List;

@Dao
public interface CarDao {
    @Query("SELECT * FROM car")
    LiveData<List<Car>> getAll();

    @Insert
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

    @Query("DELETE FROM car")
    void deleteAll();
}
