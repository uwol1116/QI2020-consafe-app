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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private LineChart lineChart;
    private ImageView calendar;

    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    int X_RANGE = 50;
    int DATA_RANGE = 30;

    ArrayList<Entry> xVal;
    LineDataSet setXcomp;
    ArrayList<String> xVals;
    ArrayList<ILineDataSet> lineDataSets;
    LineData lineData;
    TextView test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        test = findViewById(R.id.test);
        lineChart = (LineChart)findViewById(R.id.chart);


        this.InitializeView();
        this.InitializeListener();


    }
    public void InitializeView()
    {
        textView_Date = (TextView)findViewById(R.id.textView_date);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {

                final String date;
                int SSN = UserInfo.PolarSSN;//UserInfo.SSN;

                date = Integer.toString(year)+"-"+Integer.toString(monthOfYear) + "-" + Integer.toString(dayOfMonth);
                Log.d("onDataSet",Integer.toString(SSN)+date);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Server",response);

                            Gson gson=new Gson();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("history");
                            ArrayList<PolarInfo> polarInfos = new ArrayList();

                            for(int i=0;i<jsonArray.length();i++){
                                Log.d("Jaden",jsonArray.get(i).toString());
                                polarInfos.add(gson.fromJson(jsonArray.get(i).toString(),PolarInfo.class));
                            }
                            Log.d("JADEN",Integer.toString(polarInfos.get(0).getHr()));
//                            Log.d("JADEN", "DDDDDDDD");

                            if(polarInfos.size() != 0) {
                                ArrayList<Entry> heart = new ArrayList<>();
                                for (int i = 0; i < polarInfos.size(); i++) {
                                    heart.add(new Entry(i, polarInfos.get(i).getHr()));
                                }
                                LineDataSet lineDataSet = new LineDataSet(heart, "heart rate");
                                LineData lineData = new LineData(lineDataSet);

                                lineChart.setData(lineData);
                                lineChart.invalidate();
                            }

                            else {
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                };

                PolarHistoryRequest polarHistoryRequest = new PolarHistoryRequest(SSN, date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(HistoryActivity.this);
                queue.add(polarHistoryRequest);


                textView_Date.setText(date);
                //test.setText(polarInfos.get(0).getHr());


            }
        };
    }

    public void OnClickHandler(View view)
    {
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

//    private void init() {
//        chart = (LineChart) findViewById(R.id.chart);
//        charInit();
//    }
//
//    private void charInit() {
//
//        chart.setAutoScaleMinMaxEnabled(true);
//        xVal = new ArrayList<Entry>();
//        setXcomp = new LineDataSet(xVal, "X");
//        setXcomp.setColor(Color.RED);
//        setXcomp.setDrawValues(false);
//        setXcomp.setDrawCircles(false);
//        setXcomp.setAxisDependency(YAxis.AxisDependency.LEFT);
//        lineDataSets = new ArrayList<ILineDataSet>();
//        lineDataSets.add(setXcomp);
//
//        xVals = new ArrayList<String>();
//        for (int i = 0; i < X_RANGE; i++) {
//            xVals.add("");
//
//
//        }
//
//        lineData = new LineData(xVals, lineDataSets);
//        chart.setData(lineData);
//        chart.invalidate();
//    }
//
//    public void chartUpdate(int x) {
//
//        if (xVal.size() > DATA_RANGE) {
//
//            xVal.remove(0);
//            for (int i = 0; i < DATA_RANGE; i++) {
//                xVal.get(i).setXIndex(i);
//
//
//            }
//
//
//
//        }
//        xVal.add(new Entry(x, xVal.size()));
//        setXcomp.notifyDataSetChanged();
//        chart.notifyDataSetChanged();
//       chart.invalidate();
//   }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0){
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

    private void threadStart(){
        MyThread thread = new MyThread() ;
        thread.setDaemon(true);
        thread.start();

    }





}



