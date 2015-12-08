package com.tian.zhihu.network.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/8.
 */
public class LatestTopStory implements Serializable{

    public String type;
    public String id;
    public String title;
    public String image;

    public LatestTopStory(JSONObject jsn) throws JSONException {
        if (jsn.has("type")){
            type=jsn.getString("type");
        }
        if (jsn.has("id")){
            id=jsn.getString("id");
        }
        if (jsn.has("title")){
            title=jsn.getString("title");
        }
        if (jsn.has("image")){
            image=jsn.getString("image");
        }
    }
}
