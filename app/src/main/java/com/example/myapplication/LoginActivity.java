package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginActivity extends AppCompatActivity {

//    Button btn_si;
//    TextView txt_su, txt_fp;

    private EditText edit_email;
    private EditText edit_password;

//    public static Context context_Login;
//    public String IDview;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private static Handler tHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        context_Login = this;

        final EditText et_id = findViewById(R.id.et_id);
        final EditText et_pass = findViewById(R.id.et_pass);
        Button btn_si = findViewById(R.id.btn_si);
        TextView txt_su = findViewById(R.id.txt_su);
        TextView txt_fp = findViewById(R.id.txt_fp);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        fetchLocation();

        tHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String getTime = simpleDate.format(mDate);
                UserInfo.Date = getTime;


                //사용자의 현재 위치
/*                Location userLocation = getMyLocation();
                if(userLocation != null){
                    UserInfo.PolarLat = userLocation.getLatitude();
                    UserInfo.PolarLon = userLocation.getLongitude();
                    UserInfo.AirLat = userLocation.getLatitude();
                    UserInfo.AirLon = userLocation.getLongitude();
                }

*/
                fetchLocation();

//                Log.d("getTime", "time="+UserInfo.Date);
            }

        };


        class TimeRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    tHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        }
        TimeRunnable tr = new TimeRunnable();
        Thread t = new Thread(tr);
        t.start();


        //로그인 id, pw
        et_id.setText("iotteamd@gmail.com");
        et_pass.setText("a");


        //로그인 성공 실패
        btn_si.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Server",response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPass");
                                String userName = jsonObject.getString("userName");
                                String userCompany = jsonObject.getString("userCompany");
                                int userUSN = jsonObject.getInt("userUSN");

                                Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                //전역변수에 저장
                                UserInfo.userName=userName;
                                UserInfo.userID=userID;
                                UserInfo.userCompany=userCompany;
                                UserInfo.USN=userUSN;


                                intent.putExtra("userID",userID);
                                intent.putExtra("userPass",userPass);
                                intent.putExtra("userName",userName);
                                intent.putExtra("userCompany",userCompany);
                                intent.putExtra("userUSN",userUSN);


                                startActivity(intent);

                                finish();
                            }  else{
                                Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID,userPass,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        //회원가입 버튼
        txt_su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //비밀번호 찾기 버튼
        txt_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
//                    Toast.makeText(getContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    UserInfo.PolarLat = currentLocation.getLatitude();
                    UserInfo.PolarLon = currentLocation.getLongitude();
                    UserInfo.AirLat = currentLocation.getLatitude();
                    UserInfo.AirLon = currentLocation.getLongitude();

//                    Log.d("getLocation!",UserInfo.PolarLat+","+UserInfo.AirLon);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();

                }
                break;
        }
    }
}
