package com.tian.zhihu.ui.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tian.zhihu.R;
import com.tian.zhihu.base.BaseActivity;
import com.tian.zhihu.base.BitmapCache;
import com.tian.zhihu.constant.AppConstant;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.UIDataListener;
import com.tian.zhihu.network.VolleyQueueController;
import com.tian.zhihu.network.api.GetStartImageHelper;
import com.tian.zhihu.network.bean.StartImage;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;

/**
 * Created by tianshuguang on 15/11/30.
 */
public class SplashActivity extends BaseActivity implements UIDataListener<StartImage>,Animation.AnimationListener {

    private NetworkImageView img_start;

    private NetworkHelper<StartImage> imageHelper;

    private RequestQueue mQueue;

    @Override
    protected void installViews() {
        setContentView(R.layout.acty_splash);
        setTooBarVisible(View.GONE);

        img_start=(NetworkImageView)this.findViewById(R.id.img_start);

        LogUtils.e(TAG, "acty_splash");
        imageHelper=new GetStartImageHelper(this);
        imageHelper.setUiDataListener(this);
        imageHelper.sendPostRequest(AppConstant.BaseUrl + AppConstant.method_startimage, null);
    }

    @Override
    protected void registerEvents() {

    }

    @Override
    public void onDataChanged(StartImage data) {
        LogUtils.d(TAG,"text=="+data.text);
        LogUtils.d(TAG, "img==" + data.img);
        //使用volly框架直接加载图片
        initImage(data.img);
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {
        showToast("errorCode==" + errorCode + "errorMessage==" + errorMessage);
        img_start.setImageResource(R.mipmap.start);
        goActy(MainActivity.class);
        finish();
    }

    private void initImage(final String imgUrl) {
        if (ValueUtils.isStrEmpty(imgUrl)){
            img_start.setImageResource(R.mipmap.start);
        }else{
            mQueue=VolleyQueueController.getInstance(this).getRequestQueue();
            img_start.setImageUrl(imgUrl, new ImageLoader(mQueue, new BitmapCache()));
        }
        // 图片动画
        Animation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 将图片放大1.2倍，从中心开始缩放
        animation.setDuration(2000); // 动画持续时间
        animation.setFillAfter(true); // 动画结束后停留在结束的位置
        animation.setAnimationListener(SplashActivity.this); // 添加动画监听
        img_start.startAnimation(animation); // 启动动画

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // 动画结束时跳转至首页
        goActy(MainActivity.class);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
