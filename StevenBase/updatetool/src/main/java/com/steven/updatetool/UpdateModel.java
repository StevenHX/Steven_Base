package com.steven.updatetool;

import androidx.annotation.DrawableRes;

public class UpdateModel {
    private boolean isForce;
    private String title;
    private String versionName;
    private int versionCode;
    private String message;
    private String positiveStr;
    private String downloadUrl;
    private String appId;
    private String appName;
    private String negativeStr;
    private int imgSrc;
    private int bottomBg;
    private String fileMd5;



    public UpdateModel(boolean isForce, String title, String versionName,
                       int versionCode, String message, String positiveStr,
                       String downloadUrl, String appId, String appName,
                       String negativeStr, int imgSrc, int bottomBg, String fileMd5) {
        this.isForce = isForce;
        this.title = title;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.message = message;
        this.positiveStr = positiveStr;
        this.downloadUrl = downloadUrl;
        this.appId = appId;
        this.appName = appName;
        this.negativeStr = negativeStr;
        this.imgSrc = imgSrc;
        this.bottomBg = bottomBg;
        this.fileMd5 = fileMd5;
    }

    public UpdateModel() {

    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPositiveStr() {
        return positiveStr;
    }

    public void setPositiveStr(String positiveStr) {
        this.positiveStr = positiveStr;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNegativeStr() {
        return negativeStr;
    }

    public void setNegativeStr(String negativeStr) {
        this.negativeStr = negativeStr;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(@DrawableRes int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getBottomBg() {
        return bottomBg;
    }

    public void setBottomBg(int bottomBg) {
        this.bottomBg = bottomBg;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
}
