package com.tian.zhihu.network.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.bean.LongCommentsBean;

import org.json.JSONObject;

/**
 * Created by tianshuguang on 15/12/9.
 */
public class GetLongCommentsHelper extends NetworkHelper<LongCommentsBean> {

    public GetLongCommentsHelper(Context context) {
        super(context);
    }

    @Override
    protected void disposeVolleyError(VolleyError error) {
        notifyErrorHappened("8888", "网络错误");
    }

    @Override
    protected void disposeResponse(JSONObject response) {
        LongCommentsBean commentsBean=null;
        if(response != null){
            try{
                commentsBean=new LongCommentsBean(response);
                notifyDataChanged(commentsBean);
            }catch (Exception e){
                e.printStackTrace();
                notifyErrorHappened("8888", "网络错误");
            }
        } else {
            notifyErrorHappened("8888", "网络错误");
        }
    }
}
