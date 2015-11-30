package com.tian.zhihu;

import android.app.Application;

/**
 * Created by tianshuguang on 15/11/30.
 */
public class ZhihuApp extends Application{

    public static ZhihuApp appInstance;

    public static synchronized ZhihuApp getApp(){
        if (null==appInstance){
            appInstance=new ZhihuApp();
        }
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
