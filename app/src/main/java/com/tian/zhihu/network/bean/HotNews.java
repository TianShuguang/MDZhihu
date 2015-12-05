package com.tian.zhihu.network.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tianshuguang on 15/12/6.
 */
public class HotNews implements Serializable{

    public String news_id;
    public String url;
    public String thumbnail;
    public String title;

    public HotNews(JSONObject jsn) throws JSONException {
        if (jsn.has("news_id")){
            news_id=jsn.getString("news_id");
        }
        if (jsn.has("url")){
            url=jsn.getString("url");
        }
        if (jsn.has("thumbnail")){
            thumbnail=jsn.getString("thumbnail");;
        }
        if (jsn.has("title")){
            title=jsn.getString("title");
        }
    }
}
