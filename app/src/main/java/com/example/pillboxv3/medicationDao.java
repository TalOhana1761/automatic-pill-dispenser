package com.example.pillboxv3;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface medicationDao
{
    @Insert
    public void addMedication(medication m);

    @Update
    public void updateMedication(medication m);

    @Query("select * from medication")
    public List<medication> getAllMedications();

    @Delete
    public void deleteMedication(medication m);
}
