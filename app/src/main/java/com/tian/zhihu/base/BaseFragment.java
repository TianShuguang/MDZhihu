package com.tian.zhihu.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tian.zhihu.ZhihuApp;

/**
 * Created by tianshuguang on 15/11/30.
 */
public abstract class BaseFragment extends Fragment{

    protected View rootView;
    protected FrameLayout page_content;
    protected ZhihuApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app= ZhihuApp.getApp();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            page_content = new FrameLayout(getActivity());
            rootView = page_content;
            createView();
            loadData();
        }
        return rootView;
    }

    protected abstract void createView();

    protected abstract void initView(View view);

    protected abstract void loadData();

    public void setContentLayout(int layoutId) {
        View view = getActivity().getLayoutInflater().inflate(layoutId,page_content,true);
        initView(view);
    }

    public void setContentLayout(View contentLayout) {
        page_content.addView(contentLayout);
        initView(contentLayout);
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void goActy(Class acty) {
        goActy(acty, null);
    }

    public void goActy(Class acty, Bundle data) {
        Intent intent = new Intent(getActivity(), acty);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    public void goActyForResult(Class acty, int requestCode) {
        goActyForResult(acty, requestCode, null);
    }

    public void goActyForResult(Class acty, int requestCode, Bundle data) {
        Intent intent = new Intent(getActivity(), acty);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, requestCode);
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
