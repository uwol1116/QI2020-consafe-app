package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.DeviceData;
import com.example.myapplication.DeviceAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class DeviceActivity extends AppCompatActivity {

    private ArrayList<DeviceData> arrayList;
    private DeviceAdapter DeviceAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        recyclerView = findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList= new ArrayList<>();

        DeviceAdapter = new DeviceAdapter(arrayList);
        recyclerView.setAdapter(DeviceAdapter);

        Button btn_add = findViewById(R.id.btn_add);

        //add 버튼 누름 이벤트
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceData DeviceData = new DeviceData(R.drawable.sensor_icon,"Sensor Name","MAC Address");
                arrayList.add(DeviceData);
                DeviceAdapter.notifyDataSetChanged();
            }
        });



        //스와이프를 이용한 RecyclerView 삭제
//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
//                final int position = viewHolder.getAdapterPosition();
//                arrayList.remove(position);
//                DeviceAdapter.notifyItemRemoved(position);
//            }
//        };
//          recyclerView = findViewById(R.id.rv);
//        recyclerView.setHasFixedSize(true);
//        linearLayoutManager = new LinearLayoutManager(1, LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        DeviceAdapter = new MyRecyclerAdapter(arrayList);
//        recyclerView.setAdapter(DeviceAdapter);
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);

}

}
