package com.tian.zhihu.network.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.tian.zhihu.network.NetworkHelper;
import com.tian.zhihu.network.bean.StartImage;

import org.json.JSONObject;

/**
 * Created by tianshuguang on 15/11/30.
 */
public class GetStartImageHelper extends NetworkHelper<StartImage> {

    public GetStartImageHelper(Context context) {
        super(context);
    }

    @Override
    protected void disposeVolleyError(VolleyError error) {
        notifyErrorHappened("8888", "网络错误");
    }

    @Override
    protected void disposeResponse(JSONObject response) {
        StartImage image=null;
        if(response != null){
            try{
                image=new StartImage(response);
                notifyDataChanged(image);
            }catch (Exception e){
                e.printStackTrace();
                notifyErrorHappened("8888", "网络错误");
            }
        } else {
            notifyErrorHappened("8888", "网络错误");
        }
    }
}
