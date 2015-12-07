package com.tian.zhihu.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.tian.zhihu.ui.fragment.FavoriteFragment;
import com.tian.zhihu.ui.fragment.MainHotFragment;
import com.tian.zhihu.ui.fragment.NewsFragment;
import com.tian.zhihu.utils.LogUtils;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements UIDataListener<ZhihuThemeList>,View.OnClickListener {


    private RelativeLayout main_menu;
    private LinearLayout navi_login_layout;
    private TextView navi_tv_backup;
    private TextView navi_tv_download;
    private TextView navi_tv_main;

    private ListView navi_listview;
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

        // enable ActionBar app icon to behave as action to toggle nav drawer
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
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        initMenuView();
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                base_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //设置监听drawer切换
        drawerLayout.setDrawerListener(drawerToggle);
        //该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
        drawerToggle.syncState();

        MainHotFragment hotFrag=new MainHotFragment();
        showFragment(hotFrag, null, R.id.main_content);

        getZhihuTheme();
    }

    private void initMenuView(){
        main_menu= (RelativeLayout) this.findViewById(R.id.main_menu);
        View view = LayoutInflater.from(this).inflate(R.layout.frag_navigationdrawer, null);
        navi_login_layout= (LinearLayout) view.findViewById(R.id.navi_login_layout);
        navi_tv_backup= (TextView) view.findViewById(R.id.navi_tv_backup);
        navi_tv_download= (TextView) view.findViewById(R.id.navi_tv_download);
        navi_tv_main= (TextView) view.findViewById(R.id.navi_tv_main);
        navi_listview= (ListView) view.findViewById(R.id.navi_listview);
        navi_login_layout.setOnClickListener(this);
        navi_tv_backup.setOnClickListener(this);
        navi_tv_download.setOnClickListener(this);
        navi_tv_main.setOnClickListener(this);

        main_menu.addView(view);
    }
    /** Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void registerEvents() {
        navi_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                LogUtils.e(TAG, "position==" + position);
                String themeId=mList.get(position).id;
                String name=mList.get(position).name;
                getSupportActionBar().setTitle(""+name);
                NewsFragment fragment=NewsFragment.newInstance(themeId,name);
                replaceFragment(fragment, null, R.id.main_content);
            }
        });
    }

    private void getZhihuTheme(){
        themeHelper=new GetThemeHelper(this);
        themeHelper.setUiDataListener(this);
        themeHelper.sendPostRequest(AppConstant.method_themes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
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
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDataChanged(ZhihuThemeList data) {
        if (ValueUtils.isNotEmpty(data)){
            mList=data.others;
            setMenuList();
        }
    }

    private void setMenuList()
    {
        LogUtils.e(TAG,"mList.size()=="+mList.size());
        adapter=new MenuItemAdapter(MainActivity.this,mList);
        navi_listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorHappened(String errorCode, String errorMessage) {

    }

    @Override
    public void onClick(View v) {
        drawerLayout.closeDrawers();
        switch (v.getId()){
            case R.id.navi_login_layout:
                LogUtils.e(TAG,"navi_login_layout");
                break;
            case R.id.navi_tv_backup:
                LogUtils.e(TAG,"navi_tv_backup");
                FavoriteFragment favoriteFrag=new FavoriteFragment();
                showFragment(favoriteFrag,null, R.id.main_content);
                break;
            case R.id.navi_tv_download:
                LogUtils.e(TAG,"navi_tv_download");
                break;
            case R.id.navi_tv_main:
                LogUtils.e(TAG,"navi_tv_main");
                getSupportActionBar().setTitle("首页");
                MainHotFragment hotFrag=new MainHotFragment();
                showFragment(hotFrag, null, R.id.main_content);
                break;
        }
    }
}
