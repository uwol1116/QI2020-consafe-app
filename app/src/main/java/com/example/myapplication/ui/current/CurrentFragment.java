package com.example.myapplication.ui.current;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AQIListInfo;
import com.example.myapplication.AQIRealViewRequest;
import com.example.myapplication.AirQualityActivity;
import com.example.myapplication.HeartRelatedActivity;
import com.example.myapplication.R;
import com.example.myapplication.UserInfo;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CurrentFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView userName, heartValue, hr_set, aq_set, tv_polarname, tv_airname1, tv_airname2, tv_airname3, tv_airname4, coValue1, coValue2, coValue3, coValue4, so2Value1, so2Value2, so2Value3, so2Value4, no2Value1, no2Value2, no2Value3, no2Value4, o3Value1, o3Value2, o3Value3, o3Value4, pmValue1, pmValue2, pmValue3, pmValue4;
    private static Handler hHandler, tHandler, a0Handler, a1Handler, a2Handler, a3Handler;
    private Spinner spinner;
    private String[] item;
    private Button btn_set, btn_test;

    int a, b, c, d = 0;

    //현재위치 받기위해 필드 선언
    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;
    //

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_current, container, false);

        ArrayList<AQIListInfo> aqiListInfos = new ArrayList();

        userName = root.findViewById(R.id.userName);
        heartValue = root.findViewById(R.id.heartValue);
        coValue1 = root.findViewById(R.id.coValue1);
        coValue2 = root.findViewById(R.id.coValue2);
        coValue3 = root.findViewById(R.id.coValue3);
        coValue4 = root.findViewById(R.id.coValue4);
        so2Value1 = root.findViewById(R.id.so2Value1);
        so2Value2 = root.findViewById(R.id.so2Value2);
        so2Value3 = root.findViewById(R.id.so2Value3);
        so2Value4 = root.findViewById(R.id.so2Value4);
        no2Value1 = root.findViewById(R.id.no2Value1);
        no2Value2 = root.findViewById(R.id.no2Value2);
        no2Value3 = root.findViewById(R.id.no2Value3);
        no2Value4 = root.findViewById(R.id.no2Value4);
        o3Value1 = root.findViewById(R.id.o3Value1);
        o3Value2 = root.findViewById(R.id.o3Value2);
        o3Value3 = root.findViewById(R.id.o3Value3);
        o3Value4 = root.findViewById(R.id.o3Value4);
        pmValue1 = root.findViewById(R.id.pmValue1);
        pmValue2 = root.findViewById(R.id.pmValue2);
        pmValue3 = root.findViewById(R.id.pmValue3);
        pmValue4 = root.findViewById(R.id.pmValue4);
        hr_set = root.findViewById(R.id.hr_set);
        aq_set = root.findViewById(R.id.aq_set);
        tv_polarname = root.findViewById(R.id.tv_polarname);
        tv_airname1 = root.findViewById(R.id.tv_airname1);
        tv_airname2 = root.findViewById(R.id.tv_airname2);
        tv_airname3 = root.findViewById(R.id.tv_airname3);
        tv_airname4 = root.findViewById(R.id.tv_airname4);
        spinner = root.findViewById(R.id.spinner);

        //스피너 아이템 배열
        arrayList = new ArrayList<>();
        arrayList.add("List");
        arrayList.add("iot-4363");
        arrayList.add("iot-5772");
        arrayList.add("iot-4818");
        arrayList.add("iot-6234");

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//        fetchLocation();

        //사용자의 위치 수신을 위한 세팅
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);


        //이름을 전역변수에 저장
        userName.setText(UserInfo.userName);


        //스피너
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(arrayAdapter);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] temp = new String[5];
                String[] no2= new String[5];
                String[] o3 = new String[5];
                String[] co = new String[5];
                String[] so2 = new String[5];
                String[] pm = new String[5];


                switch (position) {
                    case 0:
                        break;

                    case 1:

                        Toast.makeText(getActivity(), "iot-4363", Toast.LENGTH_SHORT).show();

                        a0Handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {

                                Response.Listener<String> responseListener0 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d("Server",response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            temp[position] = jsonObject.getString("temp");
                                            no2[position] = jsonObject.getString("no2");
                                            o3[position] = jsonObject.getString("o3");
                                            co[position] = jsonObject.getString("co");
                                            so2[position] = jsonObject.getString("so2");
                                            pm[position] = jsonObject.getString("pm");


                                        } catch (JSONException e) {
                                            e.printStackTrace();

                                        }

                                    }
                                };

                                AQIRealViewRequest aqiRealViewRequest0 = new AQIRealViewRequest(95, responseListener0);
                                RequestQueue queue0 = Volley.newRequestQueue(getActivity());
                                queue0.add(aqiRealViewRequest0);
                                tv_airname1.setText("iot-4363");


                                coValue1.setText(co[position]);
                                so2Value1.setText(so2[position]);
                                no2Value1.setText(no2[position]);
                                o3Value1.setText(o3[position]);
                                pmValue1.setText(pm[position]);

                            }

                        };

                        class AQ0Runnable implements Runnable {
                            @Override
                            public void run() {
                                while (true) {
                                    a0Handler.sendEmptyMessage(0);
                                    try {
                                        Thread.sleep(3000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        }
                        AQ0Runnable ar0 = new AQ0Runnable();
                        Thread thr0 = new Thread(ar0);
                        thr0.start();

                        break;

                    case 2:

                        Toast.makeText(getActivity(), "iot-5772", Toast.LENGTH_SHORT).show();

                        a1Handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {

                                Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d("Server",response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            temp[position] = jsonObject.getString("temp");
                                            no2[position] = jsonObject.getString("no2");
                                            o3[position] = jsonObject.getString("o3");
                                            co[position] = jsonObject.getString("co");
                                            so2[position] = jsonObject.getString("so2");
                                            pm[position] = jsonObject.getString("pm");


                                        } catch (JSONException e) {
                                            e.printStackTrace();

                                        }

                                    }
                                };

                                AQIRealViewRequest aqiRealViewRequest1 = new AQIRealViewRequest(99, responseListener1);
                                RequestQueue queue1 = Volley.newRequestQueue(getActivity());
                                queue1.add(aqiRealViewRequest1);
                                tv_airname2.setText("iot-5772");


                                coValue2.setText(co[position]);
                                so2Value2.setText(so2[position]);
                                no2Value2.setText(no2[position]);
                                o3Value2.setText(o3[position]);
                                pmValue2.setText(pm[position]);

                            }

                        };


                        class AQ1Runnable implements Runnable {
                            @Override
                            public void run() {
                                while (true) {
                                    a1Handler.sendEmptyMessage(0);
                                    try {
                                        Thread.sleep(3000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        }
                        AQ1Runnable ar1 = new AQ1Runnable();
                        Thread thr1 = new Thread(ar1);
                        thr1.start();

                        break;

                    case 3:

                        Toast.makeText(getActivity(), "iot-4818", Toast.LENGTH_SHORT).show();

                        a2Handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {

                                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d("Server",response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            temp[position] = jsonObject.getString("temp");
                                            no2[position] = jsonObject.getString("no2");
                                            o3[position] = jsonObject.getString("o3");
                                            co[position] = jsonObject.getString("co");
                                            so2[position] = jsonObject.getString("so2");
                                            pm[position] = jsonObject.getString("pm");


                                        } catch (JSONException e) {
                                            e.printStackTrace();

                                        }

                                    }
                                };

                                AQIRealViewRequest aqiRealViewRequest2 = new AQIRealViewRequest(102, responseListener2);
                                RequestQueue queue2 = Volley.newRequestQueue(getActivity());
                                queue2.add(aqiRealViewRequest2);
                                tv_airname3.setText("iot-4818");


                                coValue3.setText(co[position]);
                                so2Value3.setText(so2[position]);
                                no2Value3.setText(no2[position]);
                                o3Value3.setText(o3[position]);
                                pmValue3.setText(pm[position]);

                            }

                        };


                        class AQ2Runnable implements Runnable {
                            @Override
                            public void run() {
                                while (true) {
                                    a2Handler.sendEmptyMessage(0);
                                    try {
                                        Thread.sleep(3000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        }
                        AQ2Runnable ar2 = new AQ2Runnable();
                        Thread thr2 = new Thread(ar2);
                        thr2.start();

                        break;

                    case 4:

                        Toast.makeText(getActivity(), "iot-6232", Toast.LENGTH_SHORT).show();

                        a3Handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {

                                Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d("Server",response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            temp[position] = jsonObject.getString("temp");
                                            no2[position] = jsonObject.getString("no2");
                                            o3[position] = jsonObject.getString("o3");
                                            co[position] = jsonObject.getString("co");
                                            so2[position] = jsonObject.getString("so2");
                                            pm[position] = jsonObject.getString("pm");

                                        } catch (JSONException e) {
                                            e.printStackTrace();

                                        }

                                    }
                                };

                                AQIRealViewRequest aqiRealViewRequest3 = new AQIRealViewRequest(103, responseListener3);
                                RequestQueue queue3 = Volley.newRequestQueue(getActivity());
                                queue3.add(aqiRealViewRequest3);

                                tv_airname4.setText("iot-6234");


                                coValue4.setText(co[position]);
                                so2Value4.setText(so2[position]);
                                no2Value4.setText(no2[position]);
                                o3Value4.setText(o3[position]);
                                pmValue4.setText(pm[position]);

                            }

                        };


                        class AQ3Runnable implements Runnable {
                            @Override
                            public void run() {
                                while (true) {
                                    a3Handler.sendEmptyMessage(0);
                                    try {
                                        Thread.sleep(3000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        }
                        AQ3Runnable ar3 = new AQ3Runnable();
                        Thread thr3 = new Thread(ar3);
                        thr3.start();

                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


/*        tHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String getTime = simpleDate.format(mDate);
                UserInfo.Date = getTime;

                //사용자의 현재 위치
//                Location userLocation = getMyLocation();
//                if(userLocation != null){
//                    UserInfo.PolarLat = userLocation.getLatitude();
//                    UserInfo.PolarLon = userLocation.getLongitude();
//                    UserInfo.AirLat = userLocation.getLatitude();
//                    UserInfo.AirLon = userLocation.getLongitude();
//                }

//                fetchLocation();

                Log.d("getLocation!", currentLocation.getLatitude() + "" + currentLocation.getLongitude());
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
*/

        hHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (UserInfo.PolarOperate != false) {
                    tv_polarname.setText(UserInfo.PolarDeviceName);
                    heartValue.setText(UserInfo.PolarHRDatas);
                } else {
                    heartValue.setText("");
                    tv_polarname.setText("");
                }

            }

        };


        class HRRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    hHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        }
        HRRunnable hr = new HRRunnable();
        Thread th = new Thread(hr);
        th.start();


/*        aHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (UserInfo.AirOperate != false) {
                    tv_airname1.setText(UserInfo.AirDeviceName);
                    tv_airname2.setText(UserInfo.AirDeviceName);
                    tv_airname3.setText(UserInfo.AirDeviceName);
                    tv_airname4.setText(UserInfo.AirDeviceName);
                    tv_airname5.setText(UserInfo.AirDeviceName);

                    coValue.setText(String.format("%.2f", UserInfo.raw_CO));
                    so2Value.setText(String.format("%.2f", UserInfo.raw_SO2));
                    no2Value.setText(String.format("%.2f", UserInfo.raw_NO2));
                    o3Value.setText(String.format("%.2f", UserInfo.raw_O3));
                    pmValue.setText(String.format("%.2f", UserInfo.raw_PM));
                } else {
                    tv_airname1.setText("");
                    tv_airname2.setText("");
                    tv_airname3.setText("");
                    tv_airname4.setText("");
                    tv_airname5.setText("");

                    coValue.setText("");
                    so2Value.setText("");
                    no2Value.setText("");
                    o3Value.setText("");
                    pmValue.setText("");
                }

            }

        };


        class AQRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    aHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        }
        AQRunnable ar = new AQRunnable();
        Thread thr = new Thread(ar);
        thr.start();
*/

        hr_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HeartRelatedActivity.class);
                startActivity(intent);
            }
        });


        aq_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (UserInfo.i == 0) {
                    Intent intent1 = new Intent(getActivity(), AirQualityActivity.class);
                    startActivity(intent1);
//                    UserInfo.i++;
//                } else {
//                    getActivity().finish();
//                }

            }
        });


        return root;
    }

/*    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        tHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String getTime = simpleDate.format(mDate);
                UserInfo.Date = getTime;


                //사용자의 현재 위치
                Location userLocation = getMyLocation();
                if(userLocation != null){
                    UserInfo.PolarLat = userLocation.getLatitude();
                    UserInfo.PolarLon = userLocation.getLongitude();
                    UserInfo.AirLat = userLocation.getLatitude();
                    UserInfo.AirLon = userLocation.getLongitude();
                }


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
                t.start();*/
            }


            //현재 위치 getMYLocation 메소드
/*            private Location getMyLocation() {

                Location currentLocation = null;
                // Register the listener with the Location Manager to receive location updates
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    System.out.println("////////////사용자에게 권한을 요청해야함");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
                    getMyLocation(); //권한 승인하면 즉시 위치값 받아오려고
                } else {
//                    System.out.println("////////////권한요청 안해도됨");

                    // 수동으로 위치 구하기
                    String locationProvider = LocationManager.GPS_PROVIDER;
                    currentLocation = locationManager.getLastKnownLocation(locationProvider);
                    if (currentLocation != null) {
                        double lng = currentLocation.getLongitude();
                        double lat = currentLocation.getLatitude();
                    }
                }
                return currentLocation;
            }
*/
/*            private void fetchLocation() {
                if (ActivityCompat.checkSelfPermission(
                        getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
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
*/
//        }


