package com.example.pillboxv3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// onConflict = 5

@Dao
public interface timestampDAO {
    @Insert()
    public void addTimeStamp(timestamp timestamp);

    @Update
    public void updateTimeStamp(timestamp timestamp);

    @Delete()
    public int deleteTimeStamp(timestamp ts);

    @Query("select * from timestamp")
    public List<timestamp> getAllTimestamps();

    @Query("select * from timestamp WHERE day = :givenDay")
    public List<timestamp> getTimestampsByDay(int givenDay);

    @Query("select medication from timestamp")
    public int getMedication();

    @Delete
    public void deleteAll(List<timestamp> t);

    //add here more functions to perform on database
}
