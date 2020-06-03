package com.ds.roomsample;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "name_table")
public class Name {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="name")
    private String name;

    public Name(String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
