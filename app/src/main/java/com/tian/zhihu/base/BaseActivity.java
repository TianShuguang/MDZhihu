package com.tian.zhihu.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tian.zhihu.R;
import com.tian.zhihu.ZhihuApp;
import com.tian.zhihu.utils.LogUtils;

/**
 * Created by tianshuguang on 15/11/30.
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected static String TAG;// /当前Activity的名称
    protected Context mContext;// 上下文

    protected FragmentManager fm;
    protected Fragment currentFragment;

    protected ZhihuApp app;
    protected Toolbar base_toolbar;
    protected FrameLayout base_content;
    /**
     * 屏幕的宽度和高度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app= ZhihuApp.getApp();
        app.add(this);
        fm=getSupportFragmentManager();

        /**
         * 获取屏幕宽度和高度
         */
        DisplayMetrics metric = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;

        initBaseData();
        installViews();
        registerEvents();
    }

    private void initBaseData(){
        mContext = this;
        TAG = this.getClass().getSimpleName();

        super.setContentView(R.layout.acty_base);
        base_toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        base_content = (FrameLayout) findViewById(R.id.base_content);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater=LayoutInflater.from(BaseActivity.this);
        inflater.inflate(layoutResID, base_content);
    }

    @Override
    public void setContentView(View view) {
        base_content.addView(view);
    }

    public void setTooBarVisible(int visible){
        if (0!=visible){
            base_toolbar.setVisibility(visible);
        }
    }

    /**
     * 加载布局文件
     */
    protected abstract void installViews();

    /**
     * Activity中所有View的绑定事件，都放置到该类中。
     */
    protected abstract void registerEvents();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.remove(this);
    }

    /******************************Fragment管理********START************************************/

    /**
     * 显示内容
     * @param fragment
     *      fragment.class
     */
    public void showFragment(Fragment fragment,Bundle bundle,int contentId){
        showWithFragment(fragment, fragment.getClass().getSimpleName(), bundle, contentId);
    }

    /**
     * 替换成另一个fragment
     * @param fragment
     *      fragment.class
     * @param tag
     *      一个fragment实例对应一个tag,tag不能为空和“”，且必须唯一
     */
    public void showWithFragment(Fragment fragment,String tag,Bundle bundle,int contentId){
        if(fragment == null)
            return;
        if(tag == null || tag.equals("")){
            throw new RuntimeException("replaceContentWithFragment中fragment对应的tag为null或空");
        }
        Fragment cf = fm.findFragmentByTag(tag);
        if(cf == null){
            try {
                cf = fragment;
                if(bundle != null)
                    cf.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("初始化fragment失败");
            }
        }else{
            if(cf == currentFragment)
                return;
        }
        currentFragment = cf;
        FragmentTransaction ft = fm.beginTransaction();
        //replace替换当前的viewGroup内容,add则不替换会依次累加
        ft.replace(contentId, cf, tag);
        ft.commit();

    }

    /**
     * 改变内容
     * @param fragment
     * @param bundle
     *      fragment的参数
     * @param contentId
     *      操作的layout的Id
     */
    public void replaceFragment(Fragment fragment,Bundle bundle,int contentId){
        replaceWithFragment(fragment, fragment.getClass().getSimpleName(), bundle, contentId);
    }
    public void replaceWithFragment(Fragment fragment,String tag,Bundle bundle,int contentId){
        if(fragment == null)
            return;
        if(tag == null || tag.equals("")){
            throw new RuntimeException("replaceContentWithFragment中fragment对应的tag为null或空");
        }
        Fragment cf = null;

        FragmentTransaction ft = fm.beginTransaction();
        if(currentFragment != null){
            LogUtils.e(TAG, "ft.detach(currentFragment)");
            ft.remove(currentFragment);
            currentFragment=null;
        }
        if(cf == null){
            LogUtils.e(TAG,"cf == null");
            try {
                cf = fragment;
                if(bundle != null)
                    cf.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("初始化fragment失败");
            }
            ft.replace(contentId, cf, tag);
        }else{
            if(cf == currentFragment)
                return;
            else{
                ft.attach(cf);
            }
        }
        ft.commit();
        currentFragment = cf;
    }

    public void setCurrentFragment(Fragment fragment){
        currentFragment = fragment;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
    /*********************************Fragment管理*******END**************************************/

    /*********************************Activity跳转*******START************************************/
    public void goActy(Class acty) {
        goActy(acty, null);
    }

    public void goActy(Class acty, Bundle data) {
        Intent intent = new Intent(this, acty);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public void goActyForResult(Class acty, int requestCode) {
        goActyForResult(acty, requestCode, null);
    }

    public void goActyForResult(Class acty, int requestCode, Bundle data) {
        Intent intent = new Intent(this, acty);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }
    /*********************************Activity跳转*******END************************************/

    /**显示Toast弹出框*/
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏键盘
     */
    public static void hideSoftKeyword(Context context) {
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View fadeView = ((Activity) context).getCurrentFocus();
        if (inputManager != null && fadeView != null) {
            inputManager.hideSoftInputFromWindow(fadeView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            fadeView.clearFocus();
        }
    }

}
