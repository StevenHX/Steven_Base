package com.hx.steven.wx_ali_Pay;

/**
 * Created by Administrator on 2017/6/8.
 */

public class PayModel {
    public int type;
    public String data;
    public String payInfo;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    @Override
    public String toString() {
        return "NowPayModel{" +
                "type=" + type +
                ", data='" + data + '\'' +
                ", payInfo='" + payInfo + '\'' +
                '}';
    }
}
