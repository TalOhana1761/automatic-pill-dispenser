package com.example.pillboxv3;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "timestamp")
public class timestamp {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "fullTimestamp")
    String fullTimestamp;
    @ColumnInfo(name = "medication")
    int medication;
    @ColumnInfo(name = "day")
    int day;
    @ColumnInfo(name = "hour")
    int hour;
    @ColumnInfo(name = "minute")
    int minute;

    public timestamp(int medication, int day , int hour, int minute)
    {
        this.medication = medication;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.fullTimestamp = String.valueOf(medication) + " " + String.valueOf(day) + " " + String.valueOf(hour) + " " + String.valueOf(minute);
    }

    public int getMedication() {
        return medication;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getId(){return id;}

    public void setMedication(int medication) {
        this.medication = medication;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
