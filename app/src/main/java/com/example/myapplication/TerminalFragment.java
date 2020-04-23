package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TerminalFragment extends Fragment implements ServiceConnection, SerialListener {

    private enum Connected { False, Pending, True }

    private String deviceAddress;
    private String newline = "\r\n";

    private TextView receiveText;

    private SerialSocket socket;
    private SerialService service;
    private boolean initialStart = true;
    private Connected connected = Connected.False;

//    private static Handler mHandler;

//    private String Value = "";


    //현재위치 받기위해 필드 선언
    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;
    //


    /*
     * Lifecycle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        deviceAddress = getArguments().getString("device");
    }

//    @Override
//    public void onDestroy() {
//        if (connected != Connected.False)
//            disconnect();
//        getActivity().stopService(new Intent(getActivity(), SerialService.class));
//        super.onDestroy();
//    }

    @Override
    public void onStart() {
        super.onStart();
        if(service != null)
            service.attach(this);
        else
            getActivity().startService(new Intent(getActivity(), SerialService.class)); // prevents service destroy on unbind from recreated activity caused by orientation change
    }

//    @Override
//    public void onStop() {
//        if(service != null && !getActivity().isChangingConfigurations())
//            service.detach();
//        super.onStop();
//    }

    @SuppressWarnings("deprecation") // onAttach(context) was added with API 23. onAttach(activity) works for all API versions
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().bindService(new Intent(getActivity(), SerialService.class), this, Context.BIND_AUTO_CREATE);
    }

//    @Override
//    public void onDetach() {
//        try { getActivity().unbindService(this); } catch(Exception ignored) {}
//        super.onDetach();
//    }

    @Override
    public void onResume() {
        super.onResume();
        if(initialStart && service !=null) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((SerialService.SerialBinder) binder).getService();
        if(initialStart && isResumed()) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
    }

    /*
     * UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terminal, container, false);
        receiveText = view.findViewById(R.id.receive_text);                          // TextView performance decreases with number of spans
        receiveText.setTextColor(getResources().getColor(R.color.colorRecieveText)); // set as default color to reduce number of spans
        receiveText.setMovementMethod(ScrollingMovementMethod.getInstance());
        TextView sendText = view.findViewById(R.id.send_text);
        View sendBtn = view.findViewById(R.id.send_btn);
        Button btn_gotomain = view.findViewById(R.id.btn_gotomain);
        Button airdisconnect_button = view.findViewById(R.id.airdisconnect_button);
        Button airconnect_button = view.findViewById(R.id.airconnect_button);

        //사용자의 위치 수신을 위한 세팅
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        sendBtn.setOnClickListener(v -> send(sendText.getText().toString()));


        airconnect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("start");
            }
        });


        airdisconnect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send("stop");

                UserInfo.AirOperate = false;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean success;
                        try {
                            Log.d("Server",response);
                            JSONObject jsonObject = new JSONObject(response);
                            success = jsonObject.getBoolean("success");
//                                Log.d("eunjae",""+success);

                            if (success){
                                Toast.makeText(getContext(),"Disconnect Success", Toast.LENGTH_SHORT).show();

//                                    textView2.setText(String.valueOf(UserInfo.SSN));
                            }  else{
                                Toast.makeText(getContext(),"Disconnect fail", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AirDisconnectRequest airdisconnectRequest = new AirDisconnectRequest(UserInfo.AirSSN,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(airdisconnectRequest);
            }
        });


        btn_gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity MA = (MainActivity) MainActivity.activity;
                MA.finish();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

//                getActivity().finish();
            }
        });

//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                Log.d("ahahahah", Value);
//            }
//
//        };


//        class NewRunnable implements Runnable {
//            @Override
//            public void run() {
//                while (true) {
//                    mHandler.sendEmptyMessage(0);
//                    try {
//                        Thread.sleep(3000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//            }
//        }

//        NewRunnable nr = new NewRunnable();
//        Thread t = new Thread(nr);
//        t.start();


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_terminal, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear) {
            receiveText.setText("");
            return true;
        } else if (id ==R.id.newline) {
            String[] newlineNames = getResources().getStringArray(R.array.newline_names);
            String[] newlineValues = getResources().getStringArray(R.array.newline_values);
            int pos = java.util.Arrays.asList(newlineValues).indexOf(newline);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Newline");
            builder.setSingleChoiceItems(newlineNames, pos, (dialog, item1) -> {
                newline = newlineValues[item1];
                dialog.dismiss();
            });
            builder.create().show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Serial + UI
     */
    private void connect() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
            String deviceName = device.getName() != null ? device.getName() : device.getAddress();
            status("connecting...");
            connected = Connected.Pending;
            socket = new SerialSocket();
            service.connect(this, "Connected to " + deviceName);
            socket.connect(getContext(), service, device);
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    private void disconnect() {
        connected = Connected.False;
        service.disconnect();
        socket.disconnect();
        socket = null;
    }

    public void send(String str) {
        if(connected != Connected.True) {
            Toast.makeText(getActivity(), "not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SpannableStringBuilder spn = new SpannableStringBuilder(str+'\n');
            spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorSendText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            receiveText.append(spn);

            //str: 작성해서 보낸 text
//            if(str.equals("start")){
//            Log.d("jamieee", "nice!");
//            }

            if(str.equals("start")){
                int SSN = UserInfo.AirSSN;
                UserInfo.AirOperate = true;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean success;
                        try {
                            Log.d("Server",response);
                            JSONObject jsonObject = new JSONObject(response);
                            success = jsonObject.getBoolean("success");
//                                Log.d("eunjae",""+success);

                            if (success){


//                                    textView2.setText(String.valueOf(UserInfo.SSN));
                            }  else{

                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AirconnectRequest airconnectRequest = new AirconnectRequest(SSN,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(airconnectRequest);
            }


            byte[] data = (str + newline).getBytes();
            socket.write(data);
        } catch (Exception e) {
            onSerialIoError(e);
        }
    }

    //현재 위치 getMYLocation 메소드
    private Location getMyLocation(){
        Location currentLocation = null;
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    System.out.println("////////////사용자에게 권한을 요청해야함");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
            getMyLocation(); //권한 승인하면 즉시 위치값 받아오려고
        }
        else {
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

    private void receive(byte[] data) {
//        receiveText.append(new String(data));

        //air value 로그
//        Log.d("airvalue", ""+new String(data));

        //사용자의 현재 위치
//        Location userLocation = getMyLocation();
//        if(userLocation != null){
//            UserInfo.AirLat = userLocation.getLatitude();
//            UserInfo.AirLon = userLocation.getLongitude();
//        }

        //현재 시간
//        long now = System.currentTimeMillis();
//        Date mDate = new Date(now);
//        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String getTime = simpleDate.format(mDate);
//        UserInfo.Date = getTime;

        //split
        String alltxt = new String(data);
        String[] split_data = alltxt.split(",");

        //split 확인 로그
//        Log.d("split",split_data[0]+" and "+split_data[1]);


        UserInfo.Temp = Float.valueOf(split_data[1]);
//        float temp = 0;
        UserInfo.raw_NO2 = Float.valueOf(split_data[2]);
//        float raw_NO2 = 1;
        UserInfo.raw_O3 = Float.valueOf(split_data[3]);
//        float raw_O3 = 2;
        UserInfo.raw_CO = Float.valueOf(split_data[4]);
//        float raw_CO = 3;
        UserInfo.raw_SO2 = Float.valueOf(split_data[5]);
//        float raw_SO2 = 4;
        UserInfo.raw_PM = Float.valueOf(split_data[6]);
//        float raw_PM = 5;
        float NO2 = 6;
        float O3 = 7;
        float CO = 8;
        float SO2 = 9;
        float PM = 10;



        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                boolean success;
                try {
                    Log.d("Server",response);
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getBoolean("success");
//                                Log.d("eunjae",""+success);

                    if (success){
//                                Toast.makeText(getApplicationContext(),"Transfer HR", Toast.LENGTH_SHORT).show();


//                                    textView2.setText(String.valueOf(UserInfo.SSN));
                    }  else{
//                                Toast.makeText(getApplicationContext(),"Transfer fail", Toast.LENGTH_SHORT).show();
                        return;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BoardStoreRequest boardstoreRequest = new BoardStoreRequest(UserInfo.AirSSN, UserInfo.AirLat, UserInfo.AirLon, UserInfo.Date, UserInfo.Temp, NO2, O3, CO, SO2, PM, UserInfo.raw_NO2, UserInfo.raw_O3, UserInfo.raw_CO, UserInfo.raw_SO2, UserInfo.raw_PM,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(boardstoreRequest);

    }

    private void status(String str) {
        SpannableStringBuilder spn = new SpannableStringBuilder(str+'\n');
        spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorStatusText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        receiveText.append(spn);
    }

    /*
     * SerialListener
     */
    @Override
    public void onSerialConnect() {
        status("connected");
        connected = Connected.True;
    }

    @Override
    public void onSerialConnectError(Exception e) {
        status("connection failed: " + e.getMessage());
        disconnect();
    }

    @Override
    public void onSerialRead(byte[] data) {
        receive(data);

    }



    @Override
    public void onSerialIoError(Exception e) {
        status("connection lost: " + e.getMessage());
        disconnect();
    }

}
