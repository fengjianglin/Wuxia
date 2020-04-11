package com.inkeast.wuxiaworld.ui.bookmark;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Query("SELECT * FROM bookmarks")
    List<Bookmark> loadAllBookmarks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookmarks(Bookmark... bookmarks);

    @Delete
    void deleteBookmarks(Bookmark... bookmarks);

}