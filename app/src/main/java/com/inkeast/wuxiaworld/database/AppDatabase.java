package com.inkeast.wuxiaworld.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.inkeast.wuxiaworld.database.Bookmark;
import com.inkeast.wuxiaworld.database.BookmarkDao;
import com.inkeast.wuxiaworld.database.History;
import com.inkeast.wuxiaworld.database.HistoryDao;

@Database(entities = {Bookmark.class, History.class}, version = 1, exportSchema= false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookmarkDao getBookmarkDao();

    public abstract HistoryDao getHistoryDao();

}