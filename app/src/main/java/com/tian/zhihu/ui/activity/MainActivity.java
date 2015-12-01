package com.tian.zhihu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tian.zhihu.R;
import com.tian.zhihu.base.BaseActivity;
import com.tian.zhihu.constant.AppConstant;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.UIDataListener;
import com.tian.zhihu.network.api.GetThemeHelper;
import com.tian.zhihu.network.bean.StartImage;
import com.tian.zhihu.network.bean.ZhihuTheme;
import com.tian.zhihu.network.bean.ZhihuThemeList;
import com.tian.zhihu.ui.adapter.MenuItemAdapter;
import com.tian.zhihu.ui.fragment.NewsFragment;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements UIDataListener<ZhihuThemeList> {

    private ListView main_menu_list;
    private MenuItemAdapter adapter;
    private ArrayList<ZhihuTheme> mList=new ArrayList<ZhihuTheme>();

    private NetworkHelper<ZhihuThemeList> themeHelper;

    private DrawerLayout drawerLayout;
    /**
     * 将action bar和drawerlayout绑定的组件
     */
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void installViews() {
        setContentView(R.layout.activity_main);

        setSupportActionBar(base_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            // 给左上角图标的左边加上一个返回的图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //必须通过调用setHomeButtonEnabled(true)方法确保这个图标能够作为一个操作项
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        findViews();
    }

    private void findViews(){
        main_menu_list= (ListView) this.findViewById(R.id.main_menu_list);
        drawerLayout= (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                base_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        //设置监听drawer切换
        drawerLayout.setDrawerListener(drawerToggle);
        //该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
        drawerToggle.syncState();

        getZhihuTheme();
    }

    @Override
    protected void registerEvents() {
        main_menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                LogUtils.e(TAG, "position==" + position);
                String themeId=mList.get(position-1).id;
                String name=mList.get(position-1).name;
                getSupportActionBar().setTitle(""+name);
                NewsFragment fragment=NewsFragment.newInstance(themeId,name);
                replaceFragment(fragment, null, R.id.main_content);
            }
        });
    }

    private void getZhihuTheme(){
        themeHelper=new GetThemeHelper(this);
        themeHelper.setUiDataListener(this);
        themeHelper.sendPostRequest(AppConstant.BaseUrl + AppConstant.method_themes, null);
    }

    private void setMenuList()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        main_menu_list.addHeaderView(inflater.inflate(R.layout.nav_header, main_menu_list, false));
        adapter=new MenuItemAdapter(this,mList);
        main_menu_list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChanged(ZhihuThemeList data) {
        if (ValueUtils.isNotEmpty(data)){
            mList=data.others;
            String themeId=mList.get(0).id;
            String name=mList.get(0).name;
            NewsFragment fragment=NewsFragment.newInstance(themeId,name);
            showFragment(fragment,null, R.id.main_content);
            setMenuList();
        }
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {

    }
}
