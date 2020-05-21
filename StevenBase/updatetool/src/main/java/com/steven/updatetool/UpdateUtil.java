package com.steven.updatetool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;

public class UpdateUtil {
    private static final String TAG = "UpdateUtil";
    private UpdateDialog updateDialog;

    public void showUpdateDialog(Activity activity, UpdateModel updateModel) {
        updateDialog = new UpdateDialog.Builder(activity)
                .setForce(updateModel.isForce())
                .setTitle(updateModel.getTitle())
                .setVersion(updateModel.getVersionStr())
                .setMessage(updateModel.getMessage())
                .setPositiveButton(updateModel.getPositiveStr(), (dialog, which) ->
                        downApk(activity, updateModel.getDownloadUrl(), updateModel.getAppId(), updateModel.getAppName()))
                .setNegativeButton(updateModel.getNegativeStr(), (dialog, which) -> dialog.dismiss())
                .create();
        updateDialog.show();
    }

    private void downApk(Activity activity, String url, String appId, String fileName) {
        SimpleNetManager.getInstance().downloadFile(
                url, appId, fileName, (isDone, present) -> {
                    Log.e("xxxxxxx", "isDone:" + isDone + ",present:" + present + "%" + ",currentThread: " + Thread.currentThread().getName());
                    activity.runOnUiThread(() -> updateDialog.setDownloadPresent(present));
                    if (isDone) {
                        try {
                            File file = FileUtil.getSaveFile(appId, "xyMall.apk");
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Uri contentUri = FileProvider.getUriForFile(activity, appId + ".fileProvider", file);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                            } else {
                                intent.setDataAndType(
                                        Uri.fromFile(file),
                                        "application/vnd.android.package-archive"
                                );
                            }
                            activity.startActivity(intent);

                        } catch (Exception e) {
                        }
                    }
                });
    }
}
