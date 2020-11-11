package com.hx.stevenbase.ui.main;

import android.Manifest;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.hx.mediaselect.constract.Code;
import com.hx.mediaselect.model.Photo;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.FlowTag.FlowTagLayout;
import com.hx.steven.component.ProgressBarView;
import com.hx.steven.util.BarColorUtils;
import com.hx.steven.viewpageTransformer.ScaleInTransformer;
import com.hx.stevenbase.BuildConfig;
import com.hx.stevenbase.R;
import com.hx.stevenbase.Realm.UserDB;
import com.orhanobut.logger.Logger;
import com.steven.updatetool.CheckAppVersionListener;
import com.steven.updatetool.UpdateModel;
import com.steven.updatetool.UpdateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {
    {
        setEnableMultiple(false);
    }

    @BindView(R.id.id_viewpager)
    ViewPager viewPager;
    @BindView(R.id.hello)
    Button hello;
    @BindView(R.id.color_flow_layout)
    FlowTagLayout colorFlowLayout;
    @BindView(R.id.pbView)
    ProgressBarView pbView;
    @BindView(R.id.selectPhoto)
    AppCompatImageView selectPhoto;

    private Realm realm;

    UpdateUtil updateUtil;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        /**初始化Realm*/
        Realm.init(this);
        //实例化数据库
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("UserRealm.realm")//文件名
                .encryptionKey(new byte[64])//加密用字段,不是64位会报错
                .schemaVersion(0)//版本号
                .build();
        realm = Realm.getInstance(config);

        RequestPermissions(0,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE);

        /**viewPager变换操作*/
        viewPager = findViewById(R.id.id_viewpager);
        viewPager.setOffscreenPageLimit(3);
        adapter = new pageAdapter(this, images);
        viewPager.setPageTransformer(false, new ScaleInTransformer());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        /**自定义progressView*/
        pbView.setMax(100);
        pbView.setProgress(43);
        updateUtil = new UpdateUtil();
        BarColorUtils.setBarColor(this, "#C1FFC1", true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private pageAdapter adapter;
    private int[] images = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.a, R.drawable.b, R.drawable.c};

    @Override
    protected int getContentId() {
        return R.layout.main_activity;
    }

    @OnClick(R.id.hello)
    public void onViewClicked() {
//        Beta.checkUpgrade();
//        FullScreenTimeDialog dialog = new FullScreenTimeDialog(this);
//        dialog.setMaxNumber(3000);
//        dialog.setCountDownListener(()->{
//            Log.e("xxxxx","countDownFinish");
//        });
//        dialog.show();
//        launch(this, WebActivity.class);

//        SimpleNetManager.getInstance().downloadBigFile(
//                "https://myunonline-xiyue.oss-cn-hangzhou.aliyuncs.com/package_sc/xylegusign306.apk",
//                BuildConfig.APPLICATION_ID, "xyMall.apk", (isDone, present) ->
//                        Log.e("xxxxxxx", "isDone:" + isDone + ",present:" + present + "%" + ",currentThread: " + Thread.currentThread().getName()))
//        ;

        UpdateUtil updateUtil = new UpdateUtil();
        updateUtil.setDebug(true);
        UpdateModel updateModel = new UpdateModel();
        updateModel.setForce(false);
        updateModel.setTitle("喜阅商城");
        updateModel.setVersionName("4.0.0");
        updateModel.setVersionCode(400);
        updateModel.setAppName("xyMall.apk");
        updateModel.setDownloadUrl("https://myunonline-xiyue.oss-cn-hangzhou.aliyuncs.com/package_sc/xy-marketlegusign400.apk");
        updateModel.setMessage("1.优化内容.....\n2.优化功能");
        updateModel.setPositiveStr("立即升级");
        updateModel.setNegativeStr("下次再说");
        updateModel.setAppId(BuildConfig.APPLICATION_ID);
        updateModel.setImgSrc(R.drawable.top_bg);
        updateModel.setBottomBg(R.drawable.btn_bg);
        updateModel.setFileMd5("F3D5CBCFB4A8F1BC902C6CFD3168D797");
        updateUtil.showUpdateDialog(this, updateModel, BuildConfig.VERSION_CODE,
                BuildConfig.BUILD_TYPE, new CheckAppVersionListener() {
                    @Override
                    public void readyToUpGrade() {
                        Logger.e("readyToUpGrade");
                    }

                    @Override
                    public void cancelUpGrade() {
                        Logger.e("cancelUpGrade");
                    }

                    @Override
                    public void noUpGrade() {
                        Logger.e("noUpGrade");
                    }
                });
    }

    @OnClick(R.id.hello2)
    public void onViewClicked2() {
        launch(this, WebActivity.class);
//        AlbumBuilder.with(this)
//                .setCount(10)
//                .setIsShowCamera(true)
//                .setFileProviderAuthority("com.hx.stevenbase.fileProvider")
//                .start(101);
    }

    //增
    private void realmInsert(Realm realm) {
        realm.beginTransaction();//开启事务
        UserDB user = realm.createObject(UserDB.class);
        user.setUserName("hxxxx");
        user.setPassWord("21321412");
        realm.commitTransaction();//提交事务
    }

    //查
    private List<Map<String, Object>> getData() {
        RealmResults<UserDB> userList = realm.where(UserDB.class)
                .findAll();
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map;
        for (int i = 0; i < userList.size(); i++) {
            String lv_username = userList.get(i).getUserName();
            String lv_password = userList.get(i).getPassWord();
            map = new HashMap<>();
            map.put("username", lv_username);
            map.put("password", lv_password);
            list.add(map);
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            //相机或相册回调
            if (requestCode == 101) {
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(Code.PHOTO_RESULT_KEY);
                Logger.d(resultPhotos);
                Glide.with(this).load(resultPhotos.get(0).getUri()).into(selectPhoto);
            }
        }
    }
}
