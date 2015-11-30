package com.tian.zhihu.utils;

import android.util.Log;

/**
 * Created by tianshuguang on 15/11/30.
 * Log帮助类
 */
public class LogUtils {
    /**
     * 开发时isDebug = true，显示Log日志方便开发，
     * 上线时isDebug = true，关闭Log日志
     * */
    public static boolean isDebug = true;

    public static void setIsDebug(boolean isTrue){
        isDebug = isTrue;
    }

    public static void d(String TAG, String msg){
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String tag, String msg){
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void t(String msg) {
        if (isDebug) {
            Log.d("tian", msg);
        }
    }

}
