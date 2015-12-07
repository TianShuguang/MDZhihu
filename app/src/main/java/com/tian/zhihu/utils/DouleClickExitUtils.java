package com.tian.zhihu.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tian.zhihu.R;
import com.tian.zhihu.ZhihuApp;

/**
 * Created by tianshuguang on 15/12/7.
 * 双击退出帮助类
 */
public class DouleClickExitUtils {

    private final Activity mActivity;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;

    public DouleClickExitUtils(Activity activity) {
        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Activity onKeyDown事件
     * */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if(isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if(mBackToast != null){
                mBackToast.cancel();
            }
            // 退出
            ZhihuApp.getApp().exit();
            return true;
        } else {
            isOnKeyBacking = true;
            if(mBackToast == null) {
                mBackToast = Toast.makeText(mActivity, R.string.tip_double_click_exit, Toast.LENGTH_SHORT);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }

    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if(mBackToast != null){
                mBackToast.cancel();
            }
        }
    };
}
