package com.tian.zhihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
import com.tian.zhihu.network.api.GetNewsHelper;
import com.tian.zhihu.network.bean.NewsContent;
import com.tian.zhihu.network.bean.StartImage;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;

/**
 * Created by tianshuguang on 15/12/3.
 */
public class NewsActivity extends BaseActivity implements UIDataListener<NewsContent> {

    private NetworkImageView content_iv_top;
    private Toolbar toolbar;
    private WebView news_webview;

    private NetworkHelper<NewsContent> newsHelper;
    private String news_id="";
    private String title="";
    private String image="";

    private RequestQueue mQueue= null;

    @Override
    protected void installViews() {
        setContentView(R.layout.acty_news_content);
        initBundleData();

        mQueue= VolleyQueueController.getInstance(mContext).getRequestQueue();

        findViews();

        newsHelper=new GetNewsHelper(this);
        newsHelper.setUiDataListener(this);
        newsHelper.sendPostRequest(AppConstant.method_news_content+news_id);
    }

    private void initBundleData(){
        Bundle bundle= getIntent().getExtras();
        news_id=bundle.getString("id");
        title=bundle.getString("title");
        image=bundle.getString("image");
    }

    private void findViews(){
        setSupportActionBar(base_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("" + title);
            // 给左上角图标的左边加上一个返回的图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //必须通过调用setHomeButtonEnabled(true)方法确保这个图标能够作为一个操作项
            getSupportActionBar().setHomeButtonEnabled(true);
            base_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        content_iv_top= (NetworkImageView) this.findViewById(R.id.content_iv_top);
//        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        mCollapsingToolbarLayout.setTitle(""+title);
        initWebView();
    }

    private void loadUrl(String url, final String shareUrl){
        if (ValueUtils.isStrNotEmpty(image)){
            content_iv_top.setImageUrl(image, new ImageLoader(mQueue, new BitmapCache()));
        }else{
            content_iv_top.setImageResource(R.mipmap.ic_launcher);
        }


        if (ValueUtils.isEmpty(news_webview)){
            initWebView();
        }
        if (ValueUtils.isStrEmpty(url)){
            LogUtils.e(TAG,"ValueUtils.isStrEmpty(url)");
            //webview loadUrl() 弹出系统浏览器解决办法
            news_webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                    view.loadUrl(shareUrl);
                    LogUtils.e(TAG, "shareUrl=="+shareUrl);
                    return true;
                }
            });
        }else {
            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";

            String html = "<html><head>" + css + "</head><body>" + url + "</body></html>";
            html = html.replace("<div class=\"img-place-holder\">", "");

            news_webview.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
        }
    }

    private void initWebView(){
        news_webview= (WebView) this.findViewById(R.id.news_webview);

        // 增加手动放大效果
        WebSettings webSettings = news_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        // 开启database storage API功能
        webSettings.setDatabaseEnabled(true);
        // 开启Application Cache功能
        webSettings.setAppCacheEnabled(true);
    }

    @Override
    protected void registerEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_share:
                LogUtils.e(TAG,"menu_share");
                break;
            case R.id.menu_collect:
                LogUtils.e(TAG,"menu_collect");
                break;
            case R.id.menu_comment:
                LogUtils.e(TAG,"menu_comment");
                Bundle bundle=new Bundle();
                bundle.putString("id",news_id);
                goActy(LongCommentsActivity.class,bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChanged(NewsContent data) {
        if (ValueUtils.isNotEmpty(data)){
            String content=data.body;
            String shareUrl=data.share_url;
            LogUtils.e(TAG,"shareUrl=="+shareUrl);
            loadUrl(content,shareUrl);
        }
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {

    }
}
