package com.example.myapplication;

public class SensorListInfo {
    private int ssn;
    private int usn;
    private String type = "";
    private String name = "";
    private String mac = "";
    private String flag = "";

    public int getSsn(){

        return ssn;
    }
    public void setSsn(int ssn){
        this.ssn = ssn;
    }
    public int getUsn(){

        return usn;
    }
    public void setUsn(int usn){
        this.usn = usn;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMac(){
        return mac;
    }

    public void setMac(String mac){
        this.mac = mac;
    }
    public String getFlag(){
        return flag;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }

}
