package com.steven.updatetool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.core.content.FileProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class UpdateUtil {
    private static final String TAG = "UpdateUtil";
    private UpdateDialog updateDialog;
    private boolean isDebug = false;

    /**
     *
     * @param isDebug 设置调试模式 默认测试模式无法弹窗
     */
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * @param activity
     * @param updateModel      远程配置数据
     * @param localVersionCode 本地versionCode
     * @param buildType        本地编译类型
     */
    public void showUpdateDialog(Activity activity, UpdateModel updateModel, int localVersionCode, String buildType) {
        if ((isDebug || !TextUtils.equals(buildType, "debug")) && localVersionCode < updateModel.getVersionCode()) {
            updateDialog = new UpdateDialog.Builder(activity)
                    .setForce(updateModel.isForce())
                    .setVersion(updateModel.getVersionName())
                    .setMessage(updateModel.getMessage())
                    .setImgSrc(updateModel.getImgSrc())
                    .setBottomBg(updateModel.getBottomBg())
                    .setPositiveButton(updateModel.getPositiveStr(), (dialog, which) ->
                            downApk(activity, updateModel.getDownloadUrl(), updateModel.getAppId(), updateModel.getAppName()))
                    .create();
            updateDialog.show();
        }
    }

    /**
     * @param activity         activity
     * @param requestUrl       请求配置地址
     * @param localVersionCode 本地versionCode
     * @param buildType        本地编译类型
     * @param imgSrc           -1 用默认
     * @param bottomBg         -1 用默认
     */
    public void showUpdateDialog(Activity activity, String requestUrl, int localVersionCode, String buildType,
                                 @DrawableRes int imgSrc, @DrawableRes int bottomBg) {
        SimpleNetManager.getInstance().doGetRequestAsync(requestUrl, (isSuccess, result) -> {
            if (isSuccess) {
                try {
                    JSONObject jsonObjectResult = new JSONObject(result);
                    JSONObject jsonObject = jsonObjectResult.getJSONObject("Data");
                    boolean isForce = jsonObject.getBoolean("isForce");
                    String title = jsonObject.getString("title");
                    String versionName = jsonObject.getString("versionName");
                    int versionCode = jsonObject.getInt("versionCode");
                    String message = jsonObject.getString("message");
                    String positiveStr = jsonObject.getString("positiveStr");
                    String downloadUrl = jsonObject.getString("downloadUrl");
                    String appId = jsonObject.getString("appId");
                    String appName = jsonObject.getString("appName");
                    String negativeStr = jsonObject.getString("negativeStr");
                    UpdateModel updateModel = new UpdateModel(isForce, title, versionName,
                            versionCode, message, positiveStr, downloadUrl, appId, appName, negativeStr,
                            imgSrc == -1 ? R.drawable.top_bg : imgSrc,
                            bottomBg == -1 ? R.drawable.btn_bg : bottomBg);
                    activity.runOnUiThread(() -> showUpdateDialog(activity, updateModel, localVersionCode, buildType));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param activity         activity
     * @param requestUrl       请求配置地址
     * @param localVersionCode 本地versionCode
     * @param buildType        本地编译类型
     */
    public void showUpdateDialog(Activity activity, String requestUrl, int localVersionCode, String buildType) {
        showUpdateDialog(activity, requestUrl, localVersionCode, buildType, R.drawable.top_bg, R.drawable.btn_bg);
    }

    private void downApk(Activity activity, String url, String appId, String fileName) {
        SimpleNetManager.getInstance().downloadFile(
                url, appId, fileName, (isDone, present) -> {
                    Log.e("xxxxxxx", "isDone:" + isDone + ",present:" + present + "%" + ",currentThread: " + Thread.currentThread().getName());
                    activity.runOnUiThread(() -> updateDialog.setDownloadPresent(present));
                    if (isDone) {
                        try {
                            File file = FileUtil.getSaveFile(appId, fileName);
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
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
