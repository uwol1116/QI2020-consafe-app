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

public class IDcancellationActivity extends AppCompatActivity {

    private EditText et_pass;
    private Button btn_pw_sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_cancellation);

        btn_pw_sm = findViewById(R.id.btn_pw_sm);
        et_pass = findViewById(R.id.et_pass);



        btn_pw_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPass = et_pass.getText().toString();
                int USN = UserInfo.USN;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Server",response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
//                                Toast.makeText(getApplicationContext(),"Change Success", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), "Please sign in with the new password!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(IDcancellationActivity.this, IDcancellationSuccessActivity.class);
                                startActivity(intent);

                                //MainActivity 종료
                              MainActivity MA = (MainActivity) MainActivity.activity;
                              MA.finish();

                                finish();
                            }  else{
                                Toast.makeText(getApplicationContext(),"Cancellation Fail", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                IDcancellationRequest IDcancellationRequest = new IDcancellationRequest(USN, userPass,responseListener);
                RequestQueue queue = Volley.newRequestQueue(IDcancellationActivity.this);
                queue.add(IDcancellationRequest);

            }
        });
    }
}
