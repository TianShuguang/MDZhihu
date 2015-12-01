package com.tian.zhihu.network.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tianshuguang on 15/12/1.
 */
public class ZhihuTheme implements Serializable{

    public String color;
    public String thumbnail;
    public String description;
    public String id;
    public String name;

    public ZhihuTheme(JSONObject jsn) throws JSONException {
        if (jsn.has("color")){
            color=jsn.getString("color");
        }
        if (jsn.has("thumbnail")){
            thumbnail=jsn.getString("thumbnail");
        }
        if (jsn.has("description")){
            description=jsn.getString("description");
        }
        if (jsn.has("id")){
            id=jsn.getString("id");
        }
        if (jsn.has("name")){
            name=jsn.getString("name");
        }
    }


}
