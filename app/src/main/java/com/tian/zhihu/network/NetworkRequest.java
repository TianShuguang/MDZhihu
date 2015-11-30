package com.tian.zhihu.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by tianshuguang on 15/11/30.
 */
public class NetworkRequest extends JsonRequest<JSONObject>{

    private Priority mPriority = Priority.HIGH;

    public Priority getmPriority() {
        return mPriority;
    }

    public void setmPriority(Priority mPriority) {
        this.mPriority = mPriority;
    }

    public NetworkRequest(int method, String url, Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        super(method, url, paramstoString(params), listener, errorListener);
    }

    public NetworkRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        this(Method.GET, url, null, listener, errorListener);
    }

    private static String paramstoString(Map<String, String> params)
    {
        if (params != null && params.size() > 0)
        {
            String paramsEncoding = "UTF-8";
            StringBuilder encodedParams = new StringBuilder();
            try
            {
                for (Map.Entry<String, String> entry : params.entrySet())
                {
                    encodedParams.append(URLEncoder.encode(entry.getKey(),paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                    encodedParams.append('&');
                }
                return encodedParams.toString();
            }catch (UnsupportedEncodingException uee){
                throw new RuntimeException("Encoding not supported: "+ paramsEncoding, uee);
            }
        }
        return null;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try{
            JSONObject jsonObject = new JSONObject(new String(response.data, "UTF-8"));
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        }catch (Exception e){
            return Response.error(new ParseError(e));
        }
    }



}
