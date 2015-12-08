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
import com.tian.zhihu.network.api.GetLatestHelper;
import com.tian.zhihu.network.bean.LatestBean;
import com.tian.zhihu.network.bean.LatestStory;
import com.tian.zhihu.network.bean.LatestTopStory;
import com.tian.zhihu.ui.activity.NewsActivity;
import com.tian.zhihu.ui.adapter.LatestAdapter;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;
import com.tian.zhihu.widget.viewpager.OnChildClickListener;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/6.
 */
public class LatestFragment extends BaseFragment implements UIDataListener<LatestBean> {

    private NetworkHelper<LatestBean> latestHelper;

    private RecyclerView news_list;
    private LatestAdapter adapter;
    private ArrayList<LatestStory> mList=new ArrayList<LatestStory>();
    private LatestBean latestBean;

    @Override
    protected void createView() {
        setContentLayout(R.layout.frag_news);
        getLatestNews();
    }

    private void getLatestNews(){
        latestHelper=new GetLatestHelper(getActivity());
        latestHelper.setUiDataListener(this);
        latestHelper.sendPostRequest(AppConstant.method_latest);
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
    public void onDataChanged(LatestBean data) {
        mList.clear();
        if (ValueUtils.isNotEmpty(data)){
            latestBean=data;
            LogUtils.d("data.stories.size()", "" + data.stories.size());
            mList=data.stories;
            adapter=new LatestAdapter(getActivity(),latestBean,new ChildListener());
            news_list.setAdapter(adapter);

            this.adapter.setOnItemClickListener(new ItemClickListener());
        }
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {

    }

    class ChildListener implements OnChildClickListener{

        @Override
        public void onClick(LatestTopStory story) {
            LogUtils.e("ChildListener","title=="+story.title);
        }
    }

    class ItemClickListener implements LatestAdapter.RecyclerItemClickListener {

        @Override
        public void onItemClick(View view, int postion) {
            LatestStory story=mList.get(postion);
            String id=story.id;
            String title=story.title;
            String image="";
            if (ValueUtils.isListNotEmpty(story.images)){
                image=story.images.get(0);
            }
            LogUtils.e("TAG","id=="+id);
            LogUtils.e("TAG", "title==" + title);
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("title",title);
            bundle.putString("image",image);
            goActy(NewsActivity.class,bundle);
        }
    }
}
