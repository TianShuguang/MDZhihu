package com.tian.zhihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tian.zhihu.R;
import com.tian.zhihu.base.BaseActivity;
import com.tian.zhihu.constant.AppConstant;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.UIDataListener;
import com.tian.zhihu.network.api.GetNewsHelper;
import com.tian.zhihu.network.bean.NewsContent;
import com.tian.zhihu.network.bean.StartImage;
import com.tian.zhihu.utils.ValueUtils;

/**
 * Created by tianshuguang on 15/12/3.
 */
public class NewsActivity extends BaseActivity implements UIDataListener<NewsContent> {

    private WebView news_webview;

    private NetworkHelper<NewsContent> newsHelper;
    private String id="";
    private String title="";

    @Override
    protected void installViews() {
        setContentView(R.layout.acty_news_content);
        initBundleData();

        setSupportActionBar(base_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(""+title);
            // 给左上角图标的左边加上一个返回的图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //必须通过调用setHomeButtonEnabled(true)方法确保这个图标能够作为一个操作项
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        initWebView();

        newsHelper=new GetNewsHelper(this);
        newsHelper.setUiDataListener(this);
        newsHelper.sendPostRequest(AppConstant.BaseUrl + AppConstant.method_news_content+id, null);
    }

    private void initBundleData(){
        Bundle bundle= getIntent().getExtras();
        id=bundle.getString("id");
        title=bundle.getString("title");
    }

    private void loadUrl(String url){
        if (ValueUtils.isEmpty(news_webview)){
            initWebView();
        }
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";

        String html = "<html><head>" + css + "</head><body>" + url + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");

        news_webview.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    private void initWebView(){
        news_webview= (WebView) this.findViewById(R.id.news_webview);

        // 增加手动放大效果
        WebSettings webSettings = news_webview.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8") ;
        // webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//缩放成单列
        webSettings.setBuiltInZoomControls(true);// 支持两指缩放
        webSettings.setUseWideViewPort(true);// 支持双击缩放
        webSettings.setLoadWithOverviewMode(true);// 设置全屏
        // 增加对JS的支持
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//
        // 响应gps权限
        webSettings.setDatabaseEnabled(true);
        String dir = this.getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
    }

    @Override
    protected void registerEvents() {

    }

    @Override
    public void onDataChanged(NewsContent data) {
        if (ValueUtils.isNotEmpty(data)){
            String content=data.body;
            loadUrl(content);
        }
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {

    }
}
