package com.example.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignupRequest extends StringRequest {
    final static private String URL="http://teamd-iot.calit2.net/appsignup";
    private Map<String,String> map;

    public SignupRequest(String userID, String userPassword, String userName, String userCom, Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("email",userID);
        map.put("password",userPassword);
        map.put("name",userName);
        map.put("company",userCom);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
