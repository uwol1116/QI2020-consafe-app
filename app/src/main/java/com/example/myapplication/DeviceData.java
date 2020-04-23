package com.example.myapplication;

public class DeviceData {
    private int iv_private;
    private String tv_name;
    private String tv_content;

    public DeviceData(int iv_private, String tv_name, String tv_content) {
        this.iv_private = iv_private;
        this.tv_name = tv_name;
        this.tv_content = tv_content;



    }

    public int getIv_private() {
        return iv_private;
    }

    public void setIv_private(int iv_private) {
        this.iv_private = iv_private;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_content() {
        return tv_content;
    }

    public void setTv_content(String tv_content) {
        this.tv_content = tv_content;
    }
}

