package com.tian.zhihu.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.tian.zhihu.R;
import com.tian.zhihu.base.BaseFragment;
import com.tian.zhihu.constant.AppConstant;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.UIDataListener;
import com.tian.zhihu.network.api.GetThemeContentHelper;
import com.tian.zhihu.network.api.GetThemeHelper;
import com.tian.zhihu.network.bean.ThemeContent;
import com.tian.zhihu.network.bean.ZhihuThemeList;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;

/**
 * Created by tianshuguang on 15/12/1.
 */
public class NewsFragment extends BaseFragment implements UIDataListener<ThemeContent> {

    public static String MENU_POSITION="menu_position";
    public static String MENU_NAME="menu_name";
    private static String themdId="0" ;
    private static String name="";

    private NetworkHelper<ThemeContent> themeContentHelper;

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
        LogUtils.e("MENU_NAME","name=="+name);
        getZhihuTheme(themdId);
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void loadData() {

    }

    private void getZhihuTheme(String themeId){
        themeContentHelper=new GetThemeContentHelper(getActivity());
        themeContentHelper.setUiDataListener(this);
        themeContentHelper.sendPostRequest(AppConstant.BaseUrl + AppConstant.method_themes_content+themeId, null);
    }

    @Override
    public void onDataChanged(ThemeContent data) {
        if (ValueUtils.isNotEmpty(data)){
            LogUtils.d("data.stories.size()",""+data.stories.size());
        }
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {

    }
}
