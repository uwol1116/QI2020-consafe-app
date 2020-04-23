package com.example.myapplication;

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

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText et_id;
    private Button btn_submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        et_id = findViewById(R.id.et_id);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = et_id.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Server",response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Toast.makeText(getApplicationContext(),"Transmission Success", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
//                                startActivity(intent);
                                finish();
                            }  else{
                                Toast.makeText(getApplicationContext(),"Transmission Fail", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                ForgetRequest forgetRequest = new ForgetRequest(userID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ForgetPasswordActivity.this);
                queue.add(forgetRequest);
            }
        });


    }
}
