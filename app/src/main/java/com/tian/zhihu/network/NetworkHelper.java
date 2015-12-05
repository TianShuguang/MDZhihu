package com.tian.zhihu.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tian.zhihu.constant.AppConstant;
import com.tian.zhihu.utils.LogUtils;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by tianshuguang on 15/11/30.
 */
public abstract class NetworkHelper<T> implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;

    public Context getContext() {
        return context;
    }

    public NetworkHelper(Context context) {
        this.context = context;
    }

    public void sendPostRequest(String method_url)
    {
        NetworkRequest request=getRequestForPost(AppConstant.BaseUrl + method_url);
        VolleyQueueController.getInstance(getContext()).addToRequestQueue(request);
    }

    protected NetworkRequest getRequestForPost(String url)
    {
        LogUtils.e("NetworkRequest", url);
        return new NetworkRequest(url, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        LogUtils.d("NetworkRequest", "网络错误");
        disposeVolleyError(error);
    }


    @Override
    public void onResponse(JSONObject response) {
        LogUtils.d("NetworkRequest", ""+response.toString());
        disposeResponse(response);
    }


    protected abstract void disposeVolleyError(VolleyError error);

    protected abstract void disposeResponse(JSONObject response);

    public void setUiDataListener(UIDataListener<T> uiDataListener) {
        this.uiDataListener = uiDataListener;
    }

    private UIDataListener<T> uiDataListener;



    protected void notifyDataChanged(T data){
        if(uiDataListener != null){
            uiDataListener.onDataChanged(data);
        }
    }

    protected void notifyErrorHappened(String errorCode, String errorMessage){
        if(uiDataListener != null) {
            uiDataListener.onErrorHappened(errorCode, errorMessage);
        }
    }


}
