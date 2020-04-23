package com.example.myapplication;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardStoreRequest extends StringRequest {
    final static private String URL="http://teamd-iot.calit2.net/storeboard";
    private Map<String,String> map;

    public BoardStoreRequest(int SSN, double latitude, double longitude, String Date, float temp, float NO2, float O3, float CO, float SO2, float PM, float raw_NO2, float raw_O3, float raw_CO, float raw_SO2, float raw_PM,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("ssn", String.valueOf(SSN));
        map.put("latitude", String.valueOf(latitude));
        map.put("longitude", String.valueOf(longitude));
        map.put("time", Date);
        map.put("temp", String.valueOf(temp));
        map.put("no2", String.valueOf(NO2));
        map.put("o3", String.valueOf(O3));
        map.put("co", String.valueOf(CO));
        map.put("so2", String.valueOf(SO2));
        map.put("pm", String.valueOf(PM));
        map.put("rawno2", String.valueOf(raw_NO2));
        map.put("rawo3", String.valueOf(raw_O3));
        map.put("rawco", String.valueOf(raw_CO));
        map.put("rawso2", String.valueOf(raw_SO2));
        map.put("rawpm", String.valueOf(raw_PM));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}