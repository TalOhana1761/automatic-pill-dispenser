package com.example.pillboxv3;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "medication")
public class medication
{
    @PrimaryKey
    String medicationName;

    @ColumnInfo(name = "timestamps")
    List<String> timestamps;

    public void setMedicationNameedication(String medicationName)
    {
        this.medicationName = medicationName;
    }

    public void addTimeStamp(String timestamp)
    {
        this.timestamps.add(timestamp);
    }

    public String getMedicationName()
    {
        return medicationName;
    }

    public List<String> getTimestamps()
    {
        return timestamps;
    }

    public int returnIndexOfTimestamp(String timestamp)
    {
        return this.timestamps.lastIndexOf(timestamp);
    }

    public void removeTimeStamp(String timestamp)
    {
        this.timestamps.remove(timestamp);
    }

    public void modifyTimestamp(String newTimestamp, String timestampTOModify)
    {
        this.timestamps.set(this.returnIndexOfTimestamp(timestampTOModify),newTimestamp);
    }
}
