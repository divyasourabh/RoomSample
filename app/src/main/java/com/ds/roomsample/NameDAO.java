package com.ds.roomsample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDAO {
    // allowing the insert of the same name multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Name name);

    @Query("DELETE FROM name_table")
    void deleteAll();

    @Query("SELECT * from name_table ORDER BY name ASC")
    LiveData<List<Name>> getNames();
}
