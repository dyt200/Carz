package com.example.carz.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carz.Entities.User;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE id = (:id)")
    LiveData<User> getUserById(int id);

    @Query("SELECT * FROM user WHERE email = (:email) AND password = (:password)")
    LiveData<User> validateLogin(String email, String password);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();

}
