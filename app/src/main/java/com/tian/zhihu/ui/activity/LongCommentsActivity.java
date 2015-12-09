package com.tian.zhihu.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.tian.zhihu.R;
import com.tian.zhihu.base.BaseActivity;
import com.tian.zhihu.constant.AppConstant;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.UIDataListener;
import com.tian.zhihu.network.api.GetLongCommentsHelper;
import com.tian.zhihu.network.api.GetThemeContentHelper;
import com.tian.zhihu.network.bean.LongComments;
import com.tian.zhihu.network.bean.LongCommentsBean;
import com.tian.zhihu.network.bean.ThemeContent;
import com.tian.zhihu.ui.adapter.LongCommentsAdapter;
import com.tian.zhihu.ui.adapter.NewsAdapter;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/9.
 */
public class LongCommentsActivity extends BaseActivity implements UIDataListener<LongCommentsBean> {

    private NetworkHelper<LongCommentsBean> longCommentsHelper;
    private ArrayList<LongComments> mList=new ArrayList<LongComments>();
    private RecyclerView comments_list;
    private LongCommentsAdapter adapter;

    private RelativeLayout comments_empty_layout;

    private String id="";

    @Override
    protected void installViews() {
        setContentView(R.layout.acty_long_comments);
        initData();
        findViews();
    }

    private void initData(){
        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
    }

    private void findViews(){
        setSupportActionBar(base_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("长评论");
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
        comments_empty_layout= (RelativeLayout) this.findViewById(R.id.comments_empty_layout);

        comments_list= (RecyclerView) this.findViewById(R.id.comments_list);
        // 设置LinearLayoutManager
        comments_list.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        comments_list.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        comments_list.setHasFixedSize(true);
        getLongComments(id);
    }

    private void getLongComments(String Id){
        longCommentsHelper=new GetLongCommentsHelper(this);
        longCommentsHelper.setUiDataListener(this);
        longCommentsHelper.sendPostRequest(AppConstant.method_long_comments + Id+"/long-comments");
    }

    @Override
    protected void registerEvents() {

    }

    @Override
    public void onDataChanged(LongCommentsBean data) {
        mList.clear();
        if (ValueUtils.isNotEmpty(data)){
            LogUtils.d("data.stories.size()", "" + mList.size());
            mList=data.comments;
            if (ValueUtils.isListNotEmpty(mList)){
                comments_empty_layout.setVisibility(View.GONE);
                comments_list.setVisibility(View.VISIBLE);
                adapter=new LongCommentsAdapter(this,mList);
                comments_list.setAdapter(adapter);
            }else{
                comments_empty_layout.setVisibility(View.VISIBLE);
                comments_list.setVisibility(View.GONE);
            }

        }else{
            comments_empty_layout.setVisibility(View.VISIBLE);
            comments_list.setVisibility(View.GONE);
        }

    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {
        comments_empty_layout.setVisibility(View.VISIBLE);
        comments_list.setVisibility(View.GONE);
    }
}
