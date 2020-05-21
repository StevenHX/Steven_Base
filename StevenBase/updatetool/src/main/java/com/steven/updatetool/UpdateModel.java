package com.steven.updatetool;

public class UpdateModel {
    private boolean isForce;
    private String title;
    private String versionStr;
    private String message;
    private String positiveStr;
    private String downloadUrl;
    private String appId;
    private String appName;
    private String negativeStr;

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

    public String getVersionStr() {
        return versionStr;
    }

    public void setVersionStr(String versionStr) {
        this.versionStr = versionStr;
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
}
