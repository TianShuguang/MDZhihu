package com.tian.zhihu;

import android.app.Application;

import com.tian.zhihu.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianshuguang on 15/11/30.
 */
public class ZhihuApp extends Application{

    public static ZhihuApp appInstance;

    public List<WeakReference<BaseActivity>> as = new ArrayList<WeakReference<BaseActivity>>();

    public static synchronized ZhihuApp getApp(){
        if (null==appInstance){
            appInstance=new ZhihuApp();
        }
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance=this;
    }

    /** Activity管理*/
    public void add(BaseActivity a) {
        WeakReference<BaseActivity> w = new WeakReference<BaseActivity>(a);
        as.add(w);
    }

    public void remove(BaseActivity a) {
        for (int i = 0; i < as.size(); i++) {
            WeakReference<BaseActivity> w = as.get(i);
            BaseActivity ca = w.get();
            if (a == ca) {
                as.remove(w);
                w.clear();
                break;
            }
        }
    }

    public void exit() {
        while (as.size() > 0) {
            WeakReference<BaseActivity> w = as.remove(0);
            BaseActivity a = w.get();
            if (a != null) {
                a.finish();
            }
        }
    }
}
