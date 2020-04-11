package com.inkeast.wuxiaworld.ui.history;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM histories")
    List<History> loadAllHistories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistories(History... histories);

    @Delete
    void deleteHistories(History... histories);

}