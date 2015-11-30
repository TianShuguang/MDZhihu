package com.tian.zhihu.network.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tianshuguang on 15/11/30.
 */
public class StartImage implements Serializable{

    public String text;
    public String img;

    public StartImage(JSONObject jsn) throws JSONException{
        if (jsn.has("text")){
            text=jsn.getString("text");
        }
        if (jsn.has("img")){
            img=jsn.getString("img");
        }
    }
}
