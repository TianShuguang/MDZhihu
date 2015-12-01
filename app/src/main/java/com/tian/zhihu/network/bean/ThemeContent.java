package com.tian.zhihu.network.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/1.
 */
public class ThemeContent implements Serializable{

    public String description;
    public String background;
    public String name;
    public String image;
    public ArrayList<ThemeStory> stories=new ArrayList<ThemeStory>();


    public ThemeContent(JSONObject jsn) throws JSONException {
        if (jsn.has("description")){
            description=jsn.getString("description");
        }
        if (jsn.has("background")){
            background=jsn.getString("background");
        }
        if (jsn.has("name")){
            name=jsn.getString("name");
        }
        if (jsn.has("image")){
            image=jsn.getString("image");
        }
        if (jsn.has("stories")){
            JSONArray ja=jsn.getJSONArray("stories");
            for (int i=0;i<ja.length();i++){
                stories.add(new ThemeStory(ja.getJSONObject(i)));
            }
        }
    }
}
