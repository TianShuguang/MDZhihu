package com.tian.zhihu.network.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.bean.ThemeContent;
import com.tian.zhihu.network.bean.ZhihuThemeList;

import org.json.JSONObject;

/**
 * Created by tianshuguang on 15/12/1.
 */
public class GetThemeContentHelper extends NetworkHelper{

    public GetThemeContentHelper(Context context) {
        super(context);
    }

    @Override
    protected void disposeVolleyError(VolleyError error) {
        notifyErrorHappened("8888", "网络错误");
    }

    @Override
    protected void disposeResponse(JSONObject response) {
        ThemeContent themeContent=null;
        if(response != null){
            try{
                themeContent=new ThemeContent(response);
                notifyDataChanged(themeContent);
            }catch (Exception e){
                e.printStackTrace();
                notifyErrorHappened("8888", "网络错误");
            }
        } else {
            notifyErrorHappened("8888", "网络错误");
        }
    }
}
