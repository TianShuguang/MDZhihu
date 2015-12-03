package com.tian.zhihu.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.tian.zhihu.R;
import com.tian.zhihu.base.BaseActivity;
import com.tian.zhihu.constant.AppConstant;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.UIDataListener;
import com.tian.zhihu.network.VolleyQueueController;
import com.tian.zhihu.network.api.GetStartImageHelper;
import com.tian.zhihu.network.bean.StartImage;
import com.tian.zhihu.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by tianshuguang on 15/11/30.
 */
public class SplashActivity extends BaseActivity implements UIDataListener<StartImage> {

    private ImageView loading_start;
    private NetworkHelper<StartImage> imageHelper;

    @Override
    protected void installViews() {
        setContentView(R.layout.acty_loading);
        setTooBarVisible(View.GONE);

        loading_start=(ImageView)this.findViewById(R.id.loading_start);

        LogUtils.e(TAG, "acty_loading");
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
        initImage(data.img);
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {
        showToast("errorCode==" + errorCode + "errorMessage==" + errorMessage);
        startActivity();
    }

    private void initImage(final String img) {
        File dir = getFilesDir();
        final File imgFile = new File(dir, "start.jpg");
        if (imgFile.exists()) {
            loading_start.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            loading_start.setImageResource(R.mipmap.start);
        }

        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ImageRequest irequest = new ImageRequest(img,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                saveBitmap(imgFile, bitmap);
                                startActivity();
                            }
                        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                    }
                });
                VolleyQueueController.getInstance(SplashActivity.this).getRequestQueue().add(irequest);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        loading_start.startAnimation(scaleAnim);

    }

    private void startActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }

    public void saveBitmap(File file, Bitmap bitmap) {
        if (file.exists()){
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
