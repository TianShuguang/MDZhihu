package com.tian.zhihu.network.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/9.
 */
public class LongCommentsBean implements Serializable{

    public ArrayList<LongComments> comments=new ArrayList<LongComments>();

    public LongCommentsBean(JSONObject jsn) throws JSONException {
        if (jsn.has("comments")){
            JSONArray jsonArray=jsn.getJSONArray("comments");
            for (int i=0;i<jsonArray.length();i++){
                comments.add(new LongComments(jsonArray.getJSONObject(i)));
            }
        }
    }
}
