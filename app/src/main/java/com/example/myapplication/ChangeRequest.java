package com.example.myapplication;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChangeRequest extends StringRequest {
    final static private String URL="http://teamd-iot.calit2.net/appchangepassword";
    private Map<String,String> map;

    public ChangeRequest(int USN, String userPassword, String newPassword, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("usn", String.valueOf(USN));
        map.put("password",userPassword);
        map.put("newpassword",newPassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
