package com.tian.zhihu.network.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tianshuguang on 15/12/3.
 */
public class NewsContent implements Serializable{

    public String body;
    public String title;
    public String share_url;
    public String type;
    public String id;

    public NewsContent(JSONObject jsn) throws JSONException {
        if (jsn.has("body")){
            body=jsn.getString("body");
        }
        if (jsn.has("title")){
            title=jsn.getString("title");
        }
        if (jsn.has("share_url")){
            share_url=jsn.getString("share_url");
        }
        if (jsn.has("type")){
            type=jsn.getString("type");
        }
        if (jsn.has("id")){
            id=jsn.getString("id");
        }
    }

}
