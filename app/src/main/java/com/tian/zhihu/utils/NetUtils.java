package com.tian.zhihu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by tianshuguang on 15/11/30.
 * 网络帮助类
 */
public class NetUtils {
    /**
     * @Title: isNetworkAvailable
     * @Description: 网络是否可用
     * @param @param context
     * @param @return
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = cm.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    public static boolean isAvailable(Context context){
        ConnectivityManager cManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    public static boolean isWifi(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.isConnectedOrConnecting()){
                return info.getTypeName().equalsIgnoreCase("WIFI");
            }
        }
        return false;
    }

    public static boolean isMobile(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.isConnectedOrConnecting()){
                return info.getTypeName().equalsIgnoreCase("MOBILE");
            }
        }
        return false;
    }
}
