package com.tian.zhihu.network.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/1.
 */
public class ThemeStory implements Serializable{

    public String type;
    public String id;
    public String title;
    public ArrayList<String> images=new ArrayList<String>();
    public ZhihuTheme theme;

    public ThemeStory(JSONObject jsn) throws JSONException {
        if (jsn.has("type")){
            type=jsn.getString("type");
        }
        if (jsn.has("id")){
            id=jsn.getString("id");
        }
        if (jsn.has("title")){
            title=jsn.getString("title");
        }
        if (jsn.has("images")){
            JSONArray ja=jsn.getJSONArray("images");
            for (int i=0;i<ja.length();i++){
                images.add(ja.get(i).toString());
            }
        }
        if (jsn.has("theme")){
            theme=new ZhihuTheme(jsn.getJSONObject("theme"));
        }
    }
}
