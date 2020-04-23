package com.example.myapplication;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PolarStoreRequest extends StringRequest {
    final static private String URL="http://teamd-iot.calit2.net/storepolar";
    private Map<String,String> map;

    public PolarStoreRequest(int SSN, String PolarHRDatas, double latitude, double longitude, String Date, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("ssn", String.valueOf(SSN));
        map.put("hr", PolarHRDatas);
        map.put("latitude", String.valueOf(latitude));
        map.put("longitude", String.valueOf(longitude));
        map.put("time", Date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
