package com.tian.zhihu.network.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/1.
 */
public class ZhihuThemeList implements Serializable{

    public String limit;
    public ArrayList<ZhihuTheme> others=new ArrayList<ZhihuTheme>();
    public ZhihuThemeList(JSONObject jsn) throws JSONException {
        if (jsn.has("limit")){
            limit=jsn.getString("limit");
        }
        if (jsn.has("others")){
            JSONArray ja=jsn.getJSONArray("others");
            for (int i=0;i<ja.length();i++){
                others.add(new ZhihuTheme(ja.optJSONObject(i)));
            }
        }
    }
}
