package com.inkeast.wuxiaworld;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.inkeast.wuxiaworld.ui.bookmark.Bookmark;
import com.inkeast.wuxiaworld.ui.bookmark.BookmarkDao;
import com.inkeast.wuxiaworld.ui.history.History;
import com.inkeast.wuxiaworld.ui.history.HistoryDao;

@Database(entities = {Bookmark.class, History.class}, version = 1, exportSchema= false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookmarkDao getBookmarkDao();

    public abstract HistoryDao getHistoryDao();

}