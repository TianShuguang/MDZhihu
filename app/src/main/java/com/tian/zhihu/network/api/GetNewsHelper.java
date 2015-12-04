package com.tian.zhihu.network.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.bean.NewsContent;

import org.json.JSONObject;

/**
 * Created by tianshuguang on 15/12/3.
 */
public class GetNewsHelper extends NetworkHelper<NewsContent> {

    public GetNewsHelper(Context context) {
        super(context);
    }

    @Override
    protected void disposeVolleyError(VolleyError error) {
        notifyErrorHappened("8888", "网络错误");
    }

    @Override
    protected void disposeResponse(JSONObject response) {
        NewsContent content=null;
        if(response != null){
            try{
                content=new NewsContent(response);
                notifyDataChanged(content);
            }catch (Exception e){
                e.printStackTrace();
                notifyErrorHappened("8888", "网络错误");
            }
        } else {
            notifyErrorHappened("8888", "网络错误");
        }
    }
}
