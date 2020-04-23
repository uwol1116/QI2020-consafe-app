package com.example.myapplication;

public class UserInfo {
    public static String userName = "";
    public static String userID = "";
    public static String userCompany = "";
    public static int USN;
    public static int PolarSSN;
    public static int AirSSN;
    public static String PolarDeviceID = "";
    public static String PolarDeviceName = "";
    public static  String PolarHRDatas = "";
//    public static String[] PolarHRDatas = new String[10];
    public static String AirDeviceMAC = "";
    public static String AirDeviceName = "";
    public static float Temp;
    public static float raw_NO2;
    public static float raw_O3;
    public static float raw_CO;
    public static float raw_SO2;
    public static float raw_PM;
    public static int type;
    public static String Date = "";
    public static double PolarLat;
    public static double PolarLon;
    public static double AirLat;
    public static double AirLon;
    public static boolean PolarOperate;
    public static boolean AirOperate;
    public static int i = 0;

    public UserInfo(String userName, String userID, String userCompany, int USN, int PolarSSN, int AirSSN, String PolarDeviceID, String PolarDeviceName, String PolarHRDatas, String AirDeviceMAC, String AirDeviceName, float Temp, float raw_NO2, float raw_O3, float raw_CO, float raw_SO2, float raw_PM,int type, String Date, double PolarLat, double PolarLon, double AirLat, double AirLon, boolean PolarOperate, boolean AirOperate, int i){
        this.userName = userName;
        this.userID = userID;
        this.userCompany = userCompany;
        this.USN = USN;
        this.PolarSSN = PolarSSN;
        this.AirSSN = AirSSN;
        this.PolarDeviceID = PolarDeviceID;
        this.PolarDeviceName = PolarDeviceName;
        this.PolarHRDatas = PolarHRDatas;
        this.AirDeviceMAC = AirDeviceMAC;
        this.AirDeviceName = AirDeviceName;
        this.Temp = Temp;
        this.raw_NO2 = raw_NO2;
        this.raw_O3 = raw_O3;
        this.raw_CO = raw_CO;
        this.raw_SO2 = raw_SO2;
        this.raw_PM = raw_PM;
        this.type = type;
        this.Date = Date;
        this.PolarLat = PolarLat;
        this.PolarLon = PolarLon;
        this.AirLat = AirLat;
        this.AirLon = AirLon;
        this.PolarOperate = PolarOperate;
        this.AirOperate = AirOperate;
        this.i = i;
    }

}
