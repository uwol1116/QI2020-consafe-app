package com.example.myapplication;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AirDisconnectRequest extends StringRequest {
    final static private String URL="http://teamd-iot.calit2.net/appdisconnect";
    private Map<String,String> map;

    public AirDisconnectRequest(int SSN, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("ssn", String.valueOf(SSN));

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
