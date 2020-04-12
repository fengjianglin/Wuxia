package com.inkeast.wuxiaworld.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Query("SELECT * FROM bookmarks ORDER BY id DESC")
    List<Bookmark> loadAllBookmarks();

    @Query("SELECT * FROM bookmarks where url = :url")
    List<Bookmark> loadBookmarksByUrl(String url);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookmarks(Bookmark... bookmarks);

    @Delete
    void deleteBookmarks(Bookmark... bookmarks);

    @Query("DELETE FROM bookmarks")
    void deleteAll();
}