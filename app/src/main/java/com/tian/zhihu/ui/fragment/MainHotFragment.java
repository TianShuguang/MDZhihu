package com.tian.zhihu.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tian.zhihu.R;
import com.tian.zhihu.base.BaseFragment;
import com.tian.zhihu.constant.AppConstant;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.UIDataListener;
import com.tian.zhihu.network.api.GetHotNewsHelper;
import com.tian.zhihu.network.bean.HotNews;
import com.tian.zhihu.network.bean.HotNewsList;
import com.tian.zhihu.ui.activity.NewsActivity;
import com.tian.zhihu.ui.adapter.HotNewsAdapter;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/6.
 */
public class MainHotFragment extends BaseFragment implements UIDataListener<HotNewsList> {

    private NetworkHelper<HotNewsList> hotNewsHelper;

    private RecyclerView news_list;
    private HotNewsAdapter adapter;
    private ArrayList<HotNews> mList=new ArrayList<HotNews>();

    @Override
    protected void createView() {
        setContentLayout(R.layout.frag_news);
        getHotNews();
    }

    private void getHotNews(){
        hotNewsHelper=new GetHotNewsHelper(getActivity());
        hotNewsHelper.setUiDataListener(this);
        hotNewsHelper.sendPostRequest(AppConstant.method_news_hot);
    }

    @Override
    protected void initView(View view) {
        // 拿到RecyclerView
        news_list= (RecyclerView) view.findViewById(R.id.news_list);
        // 设置LinearLayoutManager
        news_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置ItemAnimator
        news_list.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        news_list.setHasFixedSize(true);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDataChanged(HotNewsList data) {
        mList.clear();
        if (ValueUtils.isNotEmpty(data)){
            LogUtils.d("data.stories.size()", "" + data.recent.size());
            mList=data.recent;
            adapter=new HotNewsAdapter(getActivity(),mList);
            news_list.setAdapter(adapter);

            this.adapter.setOnItemClickListener(new ItemClickListener());
        }
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {

    }

    class ItemClickListener implements HotNewsAdapter.RecyclerItemClickListener {

        @Override
        public void onItemClick(View view, int postion) {
            HotNews news=mList.get(postion);
            String id=news.news_id;
            String title=news.title;
            LogUtils.e("TAG","id=="+id);
            LogUtils.e("TAG", "title==" + title);
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("title",title);
            goActy(NewsActivity.class,bundle);
        }
    }
}
