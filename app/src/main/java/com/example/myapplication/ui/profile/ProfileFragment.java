package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.ChangePasswordActivity;
import com.example.myapplication.IDcancellationActivity;
import com.example.myapplication.R;
import com.example.myapplication.UserInfo;

public class ProfileFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private TextView userName, userID, userCompany, txt_idc, txt_cpw;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = root.findViewById(R.id.userName);
        userID = root.findViewById(R.id.userID);
        userCompany = root.findViewById(R.id.userCompany);

        txt_idc = root.findViewById(R.id.txt_idc);
        txt_cpw = root.findViewById(R.id.txt_cpw);

        userName.setText(UserInfo.userName);
        userID.setText(UserInfo.userID);
        userCompany.setText(UserInfo.userCompany);

        txt_idc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IDcancellationActivity.class);
                startActivity(intent);
            }
        });

        txt_cpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent1);
            }
        });


        //pw 입력 후 user info 입장
//        Button btn_pw_sm = root.findViewById(R.id.btn_pw_sm);

//        btn_pw_sm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ?Activity.class);
//                startActivity(intent);
//            }
//        });
        return root;
    }
}