package com.hx.mediaselect.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.hx.mediaselect.constract.Code;

public class AppUtil {
    public static void startMyApplicationDetailsForResult(Activity cxt, String packageName) {
        Uri packageUri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri);
        cxt.startActivityForResult(intent, Code.REQUEST_SETTING_APP_DETAILS);
    }

    public static String getLastPathSegment(String content) {
        if (content == null || content.length() == 0) {
            return "";
        }
        String[] segments = content.split("/");
        if (segments.length > 0) {
            return segments[segments.length - 2];
        }
        return "";
    }
}
