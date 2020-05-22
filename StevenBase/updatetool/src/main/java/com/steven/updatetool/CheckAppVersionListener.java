package com.steven.updatetool;

public interface CheckAppVersionListener {
    void readyToUpGrade();

    void cancelUpGrade();

    void noUpGrade();
}
