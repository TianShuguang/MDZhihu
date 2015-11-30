package com.tian.zhihu.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tian.zhihu.ZhihuApp;

/**
 * Created by tianshuguang on 15/11/30.
 */
public class VolleyQueueController {

    private static VolleyQueueController mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private VolleyQueueController(Context context) {
        this.mContext=context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyQueueController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyQueueController(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            //getApplication()方法返回一个当前进程的全局应用上下文，这就意味着
            //它的使用情景为：你需要一个生命周期独立于当前上下文的全局上下文，
            //即就是它的存活时间绑定在进程中而不是当前某个组建。
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
