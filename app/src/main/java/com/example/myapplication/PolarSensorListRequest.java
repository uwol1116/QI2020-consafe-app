package com.example.myapplication;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PolarSensorListRequest extends StringRequest {
    final static private String URL="http://teamd-iot.calit2.net/appregisensor";
    private Map<String,String> map;

    public PolarSensorListRequest(int USN, int type, String nickname, String DEVICE_ID, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("usn", String.valueOf(USN));
        map.put("type", String.valueOf(type));
        map.put("name",nickname);
        map.put("mac",DEVICE_ID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
