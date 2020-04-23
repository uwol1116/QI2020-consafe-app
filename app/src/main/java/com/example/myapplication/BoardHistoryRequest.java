package com.example.myapplication;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardHistoryRequest extends StringRequest {
    final static private String URL="http://teamd-iot.calit2.net/boardhistory";
    private Map<String,String> map;

    public BoardHistoryRequest(int USN, String date, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("usn", String.valueOf(USN));
        map.put("time", date);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

