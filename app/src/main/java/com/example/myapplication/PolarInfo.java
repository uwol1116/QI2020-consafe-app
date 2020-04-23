package com.example.myapplication;

public class PolarInfo {
    private int psn;
    private int ssn;
    private int hr;
    private double latitude;
    private double longitude;
    private  String time;

    public int getPsn(){
        return psn;
    }
    public void setPsn(int psn){
        this.psn = psn;
    }
    public int getSsn(){
        return ssn;
    }
    public void setSsn(int ssn){
        this.ssn = ssn;
    }

    public int getHr(){
        return hr;
    }
    public void setHr(){
        this.hr = hr;
    }
   public double getLatitude(){

        return latitude;
   }
    public void setLatitude(){
        this.latitude = latitude;
    }

    public double getLongitude(){

        return longitude;
    }
    public void setLongitude(){
        this.longitude = longitude;
    }

    public String getTime(){
        return time;
    }
    public void setTime(){
        this.time = time;
    }
    @Override
    public String toString(){
        return "PolarInfo{" +  "psn='" + psn + '\'' +
                ", ssn='" + ssn + '\'' +
                ", hr='" + hr + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", time=" + time +
                '}';
    }


}
