package com.inkeast.wuxiaworld;

import android.app.Application;

import androidx.room.Room;

import com.inkeast.wuxiaworld.database.AppDatabase;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class MainApplication extends Application {

    private static AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = Room.databaseBuilder(this, AppDatabase.class, "app_database")
                .allowMainThreadQueries() // 数据库中的操作一般不在主线程 这里强行在主线层中进行操作
                .build();
        UMConfigure.init(this, "5d4d2fa63fc19512fc0000f6", "inkeast", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    public static AppDatabase getDatabase() {
        return mAppDatabase;
    }
}
