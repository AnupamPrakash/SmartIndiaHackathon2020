package com.example.newseye;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class VolleySingleton<mStack> {

    private static VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private static Context context;
    private HurlStack mStack;

    private VolleySingleton(Context context)
    {
        this.context=context;
        this.requestQueue=getRequestQueue();
    }
    public static synchronized VolleySingleton getInstance(Context context) {
        if (volleySingleton == null) {
            volleySingleton = new VolleySingleton(context);
        }
        return volleySingleton;
    }

    public RequestQueue getRequestQueue() {

        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public void addToRequestQueue(Request request)
    {
        getRequestQueue().add(request);
    }
}
