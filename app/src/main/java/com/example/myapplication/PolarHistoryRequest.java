package com.example.myapplication;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PolarHistoryRequest extends StringRequest {
    final static private String URL="http://teamd-iot.calit2.net/polarhistory";
    private Map<String,String> map;

    public PolarHistoryRequest(int SSN, String date, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("ssn", String.valueOf(SSN));
        map.put("time", date);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

