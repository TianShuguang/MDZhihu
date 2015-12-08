package com.tian.zhihu.network.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/8.
 */
public class LatestBean implements Serializable{

    public String date;
    public ArrayList<LatestStory> stories=new ArrayList<LatestStory>();
    public ArrayList<LatestStory> top_stories=new ArrayList<LatestStory>();

    public LatestBean(JSONObject jsn) throws JSONException {
        if (jsn.has("date")){
            date=jsn.getString("date");
        }
        if (jsn.has("stories")){
            JSONArray ja=jsn.getJSONArray("stories");
            for (int i=0;i<ja.length();i++){
                stories.add(new LatestStory(ja.getJSONObject(i)));
            }
        }
        if (jsn.has("top_stories")){
            JSONArray ja=jsn.getJSONArray("top_stories");
            for (int i=0;i<ja.length();i++){
                stories.add(new LatestStory(ja.getJSONObject(i)));
            }
        }
    }
}
