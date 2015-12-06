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
import com.tian.zhihu.network.api.GetThemeContentHelper;
import com.tian.zhihu.network.api.GetThemeHelper;
import com.tian.zhihu.network.bean.ThemeContent;
import com.tian.zhihu.network.bean.ThemeStory;
import com.tian.zhihu.network.bean.ZhihuThemeList;
import com.tian.zhihu.ui.activity.NewsActivity;
import com.tian.zhihu.ui.adapter.NewsAdapter;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/1.
 */
public class NewsFragment extends BaseFragment implements UIDataListener<ThemeContent> {

    private static String themdId="0" ;
    private static String name="";

    private NetworkHelper<ThemeContent> themeContentHelper;

    private RecyclerView news_list;
    private NewsAdapter adapter;
    private ArrayList<ThemeStory> mList=new ArrayList<ThemeStory>();

    public static NewsFragment newInstance(String mPosition,String mName){
        NewsFragment fragment=new NewsFragment();
        themdId=mPosition;
        name=mName;
        return  fragment;
    }

    @Override
    protected void createView() {
        setContentLayout(R.layout.frag_news);

        LogUtils.e("MENU_POSITION", "position==" + themdId);
        LogUtils.e("MENU_NAME", "name==" + name);
        getZhihuTheme(themdId);
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

    private void getZhihuTheme(String themeId){
        themeContentHelper=new GetThemeContentHelper(getActivity());
        themeContentHelper.setUiDataListener(this);
        themeContentHelper.sendPostRequest(AppConstant.method_themes_content + themeId);
    }

    @Override
    public void onDataChanged(ThemeContent data) {
        mList.clear();
        if (ValueUtils.isNotEmpty(data)){
            LogUtils.d("data.stories.size()",""+data.stories.size());
            mList=data.stories;
            adapter=new NewsAdapter(getActivity(),data);
            news_list.setAdapter(adapter);

            this.adapter.setOnItemClickListener(new ItemClickListener());
        }
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {

    }

    class ItemClickListener implements NewsAdapter.RecyclerItemClickListener {

        @Override
        public void onItemClick(View view, int postion) {
            ThemeStory story=mList.get(postion);
            String id=story.id;
            LogUtils.e("TAG","id=="+id);
            LogUtils.e("TAG", "title==" + name);
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("title",name);
            goActy(NewsActivity.class,bundle);
        }
    }
}
