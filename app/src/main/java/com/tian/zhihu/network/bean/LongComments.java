package com.tian.zhihu.network.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tianshuguang on 15/12/9.
 */
public class LongComments implements Serializable{

    public String author;
    public String avatar;
    public String content;
    public String time;
    public String id;
    public String likes;

    public LongComments(JSONObject jsn) throws JSONException {
        if (jsn.has("author")){
            author=jsn.getString("author");
        }
        if (jsn.has("avatar")){
            avatar=jsn.getString("avatar");
        }
        if (jsn.has("content")){
            content=jsn.getString("content");
        }
        if (jsn.has("time")){
            time=jsn.getString("time");
        }
        if (jsn.has("id")){
            id=jsn.getString("id");
        }
        if (jsn.has("likes")){
            likes=jsn.getString("likes");
        }
    }
}
