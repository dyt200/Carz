package com.example.carz.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.carz.Entities.Car;
import com.example.carz.pojo.CarWithImages;

import java.util.List;

@Dao
public interface CarDao {
    @Transaction
    @Query("SELECT * FROM car")
    LiveData<List<CarWithImages>> getAll();

    @Transaction
    @Query("SELECT * FROM car WHERE user != (:userId)")
    LiveData<List<CarWithImages>> getAllOther(String userId);

    @Transaction
    @Query("SELECT * FROM car WHERE id = (:id)")
    LiveData<CarWithImages> getCarById(int id);

    @Transaction
    @Query("SELECT * FROM car WHERE user = (:userId)")
    LiveData<List<CarWithImages>> getMyCars(String userId);

    @Transaction
    @RawQuery(observedEntities = Car.class)
    LiveData<List<CarWithImages>> getSearchResults(SimpleSQLiteQuery query);

    @Insert
    long insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

    @Query("DELETE FROM car")
    void deleteAll();
}
