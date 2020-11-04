package com.hx.mediaselect.constract;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.hx.mediaselect.ui.MediaSelectActivity;
import java.lang.ref.WeakReference;
public class AlbumBuilder {
    /**
     * 启动模式
     * CAMERA-相机
     * ALBUM-相册专辑
     * ALBUM_CAMERA-带有相机按钮的相册专辑
     */
    private enum StartupType {
        CAMERA,
        ALBUM,
        ALBUM_CAMERA
    }

    private static final String TAG = "AlbumBuilder";
    private static AlbumBuilder instance;
    private WeakReference<Activity> mActivity;
    private WeakReference<Fragment> mFragmentV;
    private WeakReference<android.app.Fragment> mFragment;
    private StartupType startupType;

    //私有构造函数，不允许外部调用，真正实例化通过静态方法实现
    private AlbumBuilder(Activity activity, StartupType startupType) {
        mActivity = new WeakReference<Activity>(activity);
        this.startupType = startupType;
    }

    private AlbumBuilder(android.app.Fragment fragment, StartupType startupType) {
        mFragment = new WeakReference<android.app.Fragment>(fragment);
        this.startupType = startupType;
    }

    private AlbumBuilder(FragmentActivity activity, StartupType startupType) {
        mActivity = new WeakReference<Activity>(activity);
        this.startupType = startupType;
    }

    private AlbumBuilder(Fragment fragment, StartupType startupType) {
        mFragmentV = new WeakReference<Fragment>(fragment);
        this.startupType = startupType;
    }

    /**
     * 内部处理相机和相册的实例
     *
     * @param activity Activity的实例
     * @return AlbumBuilder EasyPhotos的实例
     */

    private static AlbumBuilder with(Activity activity, StartupType startupType) {
        clear();
        instance = new AlbumBuilder(activity, startupType);
        return instance;
    }


    private static AlbumBuilder with(android.app.Fragment fragment, StartupType startupType) {
        clear();
        instance = new AlbumBuilder(fragment, startupType);
        return instance;
    }

    private static AlbumBuilder with(Fragment fragmentV, StartupType startupType) {
        clear();
        instance = new AlbumBuilder(fragmentV, startupType);
        return instance;
    }

    private static AlbumBuilder with(FragmentActivity activity, StartupType startupType) {
        clear();
        instance = new AlbumBuilder(activity, startupType);
        return instance;
    }

    /**
     * 创建相册
     *
     * @param activity 上下文
     * @return
     */
    public static AlbumBuilder createAlbumCamera(Activity activity) {
        return with(activity, StartupType.ALBUM_CAMERA);
    }

    public static AlbumBuilder createAlbum(Activity activity) {
        return with(activity, StartupType.ALBUM);
    }


    /**
     * 设置fileProvider字段
     *
     * @param fileProviderAuthority fileProvider字段
     * @return AlbumBuilder
     */
    public AlbumBuilder setFileProviderAuthority(String fileProviderAuthority) {
        Setting.fileProviderAuthority = fileProviderAuthority;
        return AlbumBuilder.this;
    }

    /**
     * 设置选择数
     *
     * @param selectorMaxCount 最大选择数
     * @return AlbumBuilder
     */
    public AlbumBuilder setCount(int selectorMaxCount) {
        Setting.mostSelectCount = selectorMaxCount;
        return AlbumBuilder.this;
    }


    private void setSettingParams() {
        switch (startupType) {
            case CAMERA:
                Setting.isShowCamera = true;
                break;
            case ALBUM:
                Setting.isShowCamera = false;
                break;
            case ALBUM_CAMERA:
                Setting.isShowCamera = true;
                break;
        }
    }

    /**
     * 设置启动属性
     *
     * @param requestCode startActivityForResult的请求码
     */

    public void start(int requestCode) {
        setSettingParams();
        launchEasyPhotosActivity(requestCode);
    }

    /**
     * 正式启动
     *
     * @param requestCode startActivityForResult的请求码
     */
    private void launchEasyPhotosActivity(int requestCode) {
        if (null != mActivity && null != mActivity.get()) {
            MediaSelectActivity.start(mActivity.get(), requestCode);
            return;
        }
        if (null != mFragment && null != mFragment.get()) {
            MediaSelectActivity.start(mFragment.get(), requestCode);
            return;
        }
        if (null != mFragmentV && null != mFragmentV.get()) {
            MediaSelectActivity.start(mFragmentV.get(), requestCode);
        }
    }

    /**
     * 清除所有数据
     */
    private static void clear() {
        Setting.clear();
        instance = null;
    }

}
