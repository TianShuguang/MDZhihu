package com.tian.zhihu.network.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.bean.HotNewsList;

import org.json.JSONObject;

/**
 * Created by tianshuguang on 15/12/6.
 */
public class GetHotNewsHelper extends NetworkHelper {

    public GetHotNewsHelper(Context context) {
        super(context);
    }

    @Override
    protected void disposeVolleyError(VolleyError error) {
        notifyErrorHappened("8888", "网络错误");
    }

    @Override
    protected void disposeResponse(JSONObject response) {
        HotNewsList themeList=null;
        if(response != null){
            try{
                themeList=new HotNewsList(response);
                notifyDataChanged(themeList);
            }catch (Exception e){
                e.printStackTrace();
                notifyErrorHappened("8888", "网络错误");
            }
        } else {
            notifyErrorHappened("8888", "网络错误");
        }
    }
}
