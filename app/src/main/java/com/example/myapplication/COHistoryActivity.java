package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class COHistoryActivity extends AppCompatActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener{
    private GoogleMap mMap;
    private ImageView calendar;
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    public LineChart lineChart;
    public ArrayList<BoardInfo> boardInfos = new ArrayList();
    public ArrayList<SensorListInfo> sensorInfos = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm25);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toast.makeText(this, "CO", Toast.LENGTH_SHORT).show();
        lineChart = (LineChart)findViewById(R.id.chart);
        this.InitializeView();
        this.InitializeListener();
    }

    public void InitializeListener() {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                final String date;
                int USN = UserInfo.USN;//UserInfo.SSN;

                date = Integer.toString(year) + "-" + Integer.toString(monthOfYear) + "-" + Integer.toString(dayOfMonth);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Server", response);
                            Gson gson = new Gson();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("history");
                            JSONArray jsonArray1 = jsonObject.getJSONArray("mydevice");

                            for (int i = 0; i < jsonArray.length(); i++) {
//                                Log.d("Jaden", jsonArray.get(i).toString());
                                boardInfos.add(gson.fromJson(jsonArray.get(i).toString(), BoardInfo.class));
                            }

                            for (int k = 0; k < jsonArray1.length(); k++){
//                                Log.d("Jaden", jsonArray1.get(k).toString());
                                sensorInfos.add(gson.fromJson(jsonArray1.get(k).toString(), SensorListInfo.class));
                            }

//                            Log.d("JADEN", Integer.toString(boardInfos.size()));
//                            Log.d("JADEN", "DDDDDDDD");

                            for(int j = 0; j <boardInfos.size(); j++)
                                for(int l = 0; l <sensorInfos.size(); l++) {
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(new LatLng(boardInfos.get(j).getLatitude(), boardInfos.get(j).getLongtitude())).title("hh"+j);
                                    mMap.addMarker(markerOptions);
                                }




//                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
//





                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                };



                BoardHistoryRequest boardHistoryRequest = new BoardHistoryRequest(USN, date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(COHistoryActivity.this);
                queue.add(boardHistoryRequest);


                textView_Date.setText(date);
                //test.setText(polarInfos.get(0).getHr());



            }
        };
    }

    private void InitializeView() {
        textView_Date = (TextView) findViewById(R.id.textView_date);
    }

    public void OnClickHandler(View view) {
        int nYear;
        int nMonth;
        int nDay;

        Calendar calendar = new GregorianCalendar(Locale.US);
        nYear = calendar.get(Calendar.YEAR);
        nMonth = calendar.get(Calendar.MONTH) + 1;
        nDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, nYear, nMonth, nDay);

        dialog.show();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {
//               int num = Integer.parseInt(UserInfo.PolarHRData);
//               chartUpdate(num);
            }

        }

    };


    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void threadStart() {
        COHistoryActivity.MyThread thread = new COHistoryActivity.MyThread();
        thread.setDaemon(true);
        thread.start();

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng position = new LatLng(32.883215, -117.232);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 11));


        mMap.setOnMarkerClickListener(this);


//
//
//        MarkerOptions markerOptions = new MarkerOptions();
//       markerOptions.position(SEOUL);
//       markerOptions.title("서울");
//        markerOptions.snippet("한국의 수도");
//        mMap.addMarker(markerOptions);
//
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));



    }



    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this, ""+marker.getPosition(), Toast.LENGTH_SHORT).show();
        ArrayList<Entry> heart = new ArrayList<>();

        for (int i = 0; i < boardInfos.size(); i++) {



            heart.add(new Entry(i, boardInfos.get(i).getRawco()));

        }
        LineDataSet lineDataSet = new LineDataSet(heart, "CO");
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();


        return true;
    }




}