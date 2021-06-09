package com.example.mybroadcast;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication {
    public static final String TAG=MyApplication.class.getSimpleName();

    private RequestQueue requestQueue;
    private static MyApplication mInstance;

    public MyApplication(Context content){
        requestQueue=Volley.newRequestQueue(content);

    }
    public static synchronized MyApplication getInstance(Context context){
        if (mInstance==null){
            mInstance=new MyApplication(context);
        }

        return mInstance;
    }
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag){
        if(requestQueue!=null){
            requestQueue.cancelAll(tag);
        }
    }

}
