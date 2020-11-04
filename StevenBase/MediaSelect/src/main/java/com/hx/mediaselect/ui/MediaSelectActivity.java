package com.hx.mediaselect.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hx.mediaselect.R;
import com.hx.mediaselect.constract.Code;
import com.hx.mediaselect.constract.Setting;
import com.hx.mediaselect.ui.adapter.AlbumItemsAdapter;
import com.hx.mediaselect.ui.adapter.LoadImageCallBack;
import com.hx.mediaselect.ui.adapter.PhotosAdapter;
import com.hx.mediaselect.util.AppUtil;
import com.hx.mediaselect.util.PermissionUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaSelectActivity extends AppCompatActivity implements PhotosAdapter.OnClickListener, View.OnClickListener {

    private static final String TAG = "MediaSelect";

    private RecyclerView rvAlbumItems;
    private AlbumItemsAdapter albumItemsAdapter;
    private RelativeLayout rootViewAlbumItems;
    private ArrayList<Object> albumItemList = new ArrayList<>();

    private AppCompatTextView tvDone;
    private RecyclerView rvPhotos;
    private PhotosAdapter photosAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Photo> photoList = new ArrayList<>();


    private RelativeLayout permissionView;
    private TextView tvPermission;

    private AppCompatTextView tv_preview;

    public static void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, MediaSelectActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), MediaSelectActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void start(androidx.fragment.app.Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), MediaSelectActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meida_select);
        permissionView = findViewById(R.id.rl_permissions_view);
        tvPermission = findViewById(R.id.tv_permission);
        rvAlbumItems = findViewById(R.id.rv_album_items);
        rootViewAlbumItems = findViewById(R.id.root_view_album_items);
        rvPhotos = findViewById(R.id.rv_photos);
        tvDone = findViewById(R.id.tv_done);
        tv_preview = findViewById(R.id.tv_preview);
        tv_preview.setOnClickListener(this);


        if (PermissionUtil.checkAndRequestPermissionsInActivity(this, getNeedPermissions())) {
            hasPermissions();
        } else {
            permissionView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onPermissionResult(this, permissions, grantResults,
                new PermissionUtil.PermissionCallBack() {
                    @Override
                    public void onSuccess() {
                        hasPermissions();
                    }

                    @Override
                    public void onShouldShow() {
                        tvPermission.setText(R.string.permissions_again_easy_photos);
                        permissionView.setOnClickListener(view -> {
                            if (PermissionUtil.checkAndRequestPermissionsInActivity(MediaSelectActivity.this, getNeedPermissions())) {
                                hasPermissions();
                            }
                        });

                    }

                    @Override
                    public void onFailed() {
                        tvPermission.setText(R.string.permissions_die_easy_photos);
                        permissionView.setOnClickListener(view -> {
                            AppUtil.startMyApplicationDetailsForResult(MediaSelectActivity.this,
                                    getPackageName());
                        });

                    }
                });
    }

    public void hasPermissions() {
        permissionView.setVisibility(View.GONE);
        if (Setting.isShowCamera) {
            photoList.add(0, null);
        }
        getImages(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, photoList, new LoadImageCallBack() {
            @Override
            public void onComplete() {
                photosAdapter = new PhotosAdapter(MediaSelectActivity.this, photoList, MediaSelectActivity.this);
                gridLayoutManager = new GridLayoutManager(MediaSelectActivity.this, 3);
                rvPhotos.setLayoutManager(gridLayoutManager);
                rvPhotos.setAdapter(photosAdapter);
            }
        });
    }

    protected String[] getNeedPermissions() {
        if (Setting.isShowCamera) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                return new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
            }
            return new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
            }
            return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
    }

    private void getImages(Uri uri, List<Photo> list, LoadImageCallBack callBack) {
        String[] columns = {
                MediaStore.MediaColumns._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DESCRIPTION};
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC";
        Cursor externalCursor = new CursorLoader(this, uri, columns, null, null, sortOrder).loadInBackground();
        if (externalCursor != null) {
            while (externalCursor.moveToNext()) {
                Photo model = new Photo();
                model.setName(externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                model.setSize(externalCursor.getLong(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.SIZE)));
                model.setPath(externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA)));
                model.setType(externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.MIME_TYPE)));
                String id = externalCursor.getString(externalCursor.getColumnIndex(MediaStore.MediaColumns._ID));
                model.setUri(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id));
                list.add(model);
            }
            callBack.onComplete();
        }
    }

    /**
     * 判断相机驱动是否可用
     *
     * @return
     */
    public boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    /**
     * 检查设备是否有摄像头
     *
     * @return true, 有相机；false,无相机
     */
    public boolean hasCamera() {
        return hasBackFacingCamera() || hasFrontFacingCamera();
    }

    /**
     * 检查设备是否有后置摄像头
     *
     * @return true, 有后置摄像头；false,后置摄像头
     */
    public boolean hasBackFacingCamera() {
        final int CAMERA_FACING_BACK = 0;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    /**
     * 检查设备是否有前置摄像头
     *
     * @return true, 有前置摄像头；false,前置摄像头
     */
    public boolean hasFrontFacingCamera() {
        final int CAMERA_FACING_BACK = 1;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    private boolean checkCameraFacing(final int facing) {
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片
     */
    private Uri createImageUri() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
        } else {
            photoFile = getImageFileCache(System.currentTimeMillis() + ".jpg");
            return getUri(this, photoFile);
        }
        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private Uri getUri(Context cxt, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (TextUtils.isEmpty(Setting.fileProviderAuthority)) {
                throw new NullPointerException("Setting.fileProviderAuthority must not be null.");
            }
            return FileProvider.getUriForFile(cxt, Setting.fileProviderAuthority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * AndroidQ以下
     * 创建图片缓存路径
     *
     * @param fileName 名称 包含文件类型
     * @return 返回file类型
     */
    public static File getImageFileCache(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory() +
                File.separator +
                Environment.DIRECTORY_DCIM);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file + File.separator + fileName);
    }

    public void launchCamera(int requestCode) {
        if (TextUtils.isEmpty(Setting.fileProviderAuthority))
            throw new RuntimeException("AlbumBuilder" + " : 请执行 setFileProviderAuthority()方法");
        if (hasCamera() && cameraIsCanUse()) {
            toAndroidCamera(requestCode);
        } else {
            Toast.makeText(this, "相机不可用", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri photoUri = null;
    private File photoFile = null;

    private void toAndroidCamera(int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            photoUri = createImageUri();
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(cameraIntent, requestCode);
        } else {
            Toast.makeText(this, R.string.msg_no_camera_easy_photos, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCameraClick() {
        launchCamera(Code.REQUEST_CAMERA);
    }

    @Override
    public void onPhotoClick(int position, int realPosition) {
        if (photosAdapter.getSelectPhotos().size() > 0)
            PreviewActivity.start(this, photosAdapter.getSelectPhotos());
    }

    @Override
    public void onSelectorOutOfMax(@Nullable Integer result) {
        Toast.makeText(this, getString(R.string.selector_reach_max_image_hint_easy_photos,
                Setting.mostSelectCount), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelectorChanged() {
        shouldShowMenuDone();
    }

    private void shouldShowMenuDone() {
        List<Photo> selects = photosAdapter.getSelectPhotos();
        if (selects.isEmpty()) {
            if (View.VISIBLE == tvDone.getVisibility()) {
                ScaleAnimation scaleHide = new ScaleAnimation(1f, 0f, 1f, 0f);
                scaleHide.setDuration(200);
                tvDone.startAnimation(scaleHide);
            }
            tvDone.setVisibility(View.INVISIBLE);
        } else {
            if (View.INVISIBLE == tvDone.getVisibility()) {
                ScaleAnimation scaleShow = new ScaleAnimation(0f, 1f, 0f, 1f);
                scaleShow.setDuration(200);
                tvDone.startAnimation(scaleShow);
            }
            tvDone.setVisibility(View.VISIBLE);
        }
        tvDone.setText(getString(R.string.selector_action_done_easy_photos, selects.size(),
                Setting.mostSelectCount));
        tvDone.setOnClickListener(this);
    }
    private void done() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Code.PHOTO_RESULT_KEY, photosAdapter.getSelectPhotos());
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_preview) {
            if (photosAdapter.getSelectPhotos().size() > 0)
                PreviewActivity.start(this, photosAdapter.getSelectPhotos());
        } else if (id ==  R.id.tv_done) {
            done();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (Code.REQUEST_CAMERA == requestCode) {
                    MediaScannerConnection.scanFile(getApplicationContext(),
                            new String[]{Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ?
                                    photoUri.getPath() : photoFile.getAbsolutePath()},
                            null, null);
                    Photo photo = new Photo();
                    photo.setUri(photoUri);
                    photo.setType("image/jpeg");
                    photo.setPath(photoUri.getPath());
                    photoList.add(Setting.isShowCamera ? 1 : 0, photo);
                    photosAdapter.notifyData();
                    return;
                }
                break;
            case RESULT_CANCELED:
                break;
            default:
                break;
        }
    }
}