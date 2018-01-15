package com.hx.stevenbase.ui.Login;

import com.hx.steven.http.BaseDto;

/**
 * Created by user on 2018/1/15.
 */

public class LoginDto extends BaseDto {
    private String mobile;
    private String password;
    private String verifyCode;
    private String deviceId;//设备ID
    private boolean isSimulator;//是否模拟器

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isSimulator() {
        return isSimulator;
    }

    public void setSimulator(boolean simulator) {
        isSimulator = simulator;
    }
}
