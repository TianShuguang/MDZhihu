package com.tian.zhihu.network.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/6.
 */
public class HotNewsList implements Serializable{

    public ArrayList<HotNews> recent=new ArrayList<HotNews>();
    public HotNewsList(JSONObject jsn) throws JSONException {
        if (jsn.has("recent")){
            JSONArray ja=jsn.getJSONArray("recent");
            for (int i=0;i<ja.length();i++){
                recent.add(new HotNews(ja.optJSONObject(i)));
            }
        }
    }
}
