package com.tian.zhihu;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tian.zhihu.base.BaseActivity;

import java.io.File;
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

        initImageLoader(getApplicationContext());

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

    private void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir)).writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);

    }
}
