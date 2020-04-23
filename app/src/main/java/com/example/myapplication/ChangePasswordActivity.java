package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText et_pass, et_newpass;
    private Button btn_cpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btn_cpw = findViewById(R.id.btn_cpw);

        et_pass = findViewById(R.id.et_pass);
        et_newpass = findViewById(R.id.editnewPW);


        btn_cpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = et_pass.getText().toString();
                String userPass = et_newpass.getText().toString();
                int USN = UserInfo.USN;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Server",response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Toast.makeText(getApplicationContext(),"Change Success", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), "Please sign in with the new password!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivity.this,LoginActivity.class);
                                startActivity(intent);

                                //MainActivity 종료
                              MainActivity MA = (MainActivity) MainActivity.activity;
                              MA.finish();

                                finish();
                            }  else{
                                Toast.makeText(getApplicationContext(),"Change Fail", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                ChangeRequest ChangeRequest = new ChangeRequest(USN, userID,userPass,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
                queue.add(ChangeRequest);

            }
        });

    }
}




