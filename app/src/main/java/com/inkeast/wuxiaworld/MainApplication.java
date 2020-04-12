package com.inkeast.wuxiaworld;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        UMConfigure.init(this, "5d4d2fa63fc19512fc0000f6", "inkeast", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }
}
