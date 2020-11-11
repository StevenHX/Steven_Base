package com.hx.mediaselect.constract;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hx.mediaselect.ui.MediaSelectActivity;

import java.lang.ref.WeakReference;

public class AlbumBuilder {
    private static final String TAG = "AlbumBuilder";
    private static AlbumBuilder instance;
    private WeakReference<Activity> mActivity;
    private WeakReference<Fragment> mFragmentV;
    private WeakReference<android.app.Fragment> mFragment;

    //私有构造函数，不允许外部调用，真正实例化通过静态方法实现
    private AlbumBuilder(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    private AlbumBuilder(android.app.Fragment fragment) {
        mFragment = new WeakReference<android.app.Fragment>(fragment);
    }

    private AlbumBuilder(FragmentActivity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    private AlbumBuilder(Fragment fragment) {
        mFragmentV = new WeakReference<Fragment>(fragment);
    }

    /**
     * 内部处理相机和相册的实例
     *
     * @param activity Activity的实例
     * @return AlbumBuilder EasyPhotos的实例
     */

    public static AlbumBuilder with(Activity activity) {
        clear();
        instance = new AlbumBuilder(activity);
        return instance;
    }


    public static AlbumBuilder with(android.app.Fragment fragment) {
        clear();
        instance = new AlbumBuilder(fragment);
        return instance;
    }

    public static AlbumBuilder with(Fragment fragmentV) {
        clear();
        instance = new AlbumBuilder(fragmentV);
        return instance;
    }

    public static AlbumBuilder with(FragmentActivity activity) {
        clear();
        instance = new AlbumBuilder(activity);
        return instance;
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

    /**
     * 设置是否显示相机
     *
     * @param isShowCamera 是否显示相机
     * @return AlbumBuilder
     */
    public AlbumBuilder setIsShowCamera(boolean isShowCamera) {
        Setting.isShowCamera = isShowCamera;
        return AlbumBuilder.this;
    }

    public AlbumBuilder setDstWidth(int dstWidth) {
        Setting.dstWidth = dstWidth;
        return AlbumBuilder.this;
    }

    public AlbumBuilder setDstHeight(int dstHeight) {
        Setting.dstHeight = dstHeight;
        return AlbumBuilder.this;
    }

    /**
     * 设置启动属性
     *
     * @param requestCode startActivityForResult的请求码
     */

    public void start(int requestCode) {
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
