package com.hx.stevenbase.ui.main;

import android.Manifest;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;

import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.FlowTag.FlowTagLayout;
import com.hx.steven.component.ProgressBarView;
import com.hx.steven.http.BaseApiService;
import com.hx.steven.http.BaseBean;
import com.hx.steven.manager.RetrofitNetManager;
import com.hx.steven.viewpageTransformer.ScaleInTransformer;
import com.hx.stevenbase.R;
import com.hx.stevenbase.Realm.UserDB;
import com.hx.stevenbase.http.ApiService;
import com.hx.stevenbase.http.HttpCallback;
import com.hx.stevenbase.ui.Set.home.homeBean;

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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;

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

    private Realm realm;

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

        RequestPermissions(0, Manifest.permission.WRITE_EXTERNAL_STORAGE);

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
//        FullScreenTimeDialog dialog = new FullScreenTimeDialog(this);
//        dialog.setMaxNumber(3000);
//        dialog.setCountDownListener(()->{
//            Log.e("xxxxx","countDownFinish");
//        });
//        dialog.show();
//        launch(this, SetActivity.class);
//        realmInsert(realm);
//        List<Map<String, Object>> data = getData();
//        Logger.d(data);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        HttpLoggingInterceptor logging2 = new HttpLoggingInterceptor();
        logging2.setLevel(HttpLoggingInterceptor.Level.BASIC);

        RetrofitNetManager.getInstance().init("http://www.baidu.com",logging,logging2);
        ApiService apiService = (ApiService) RetrofitNetManager.getInstance().getApiService();
        apiService.getHomeArticles(0).enqueue(new HttpCallback<homeBean>() {
            @Override
            public void onSuccess(homeBean result) {

            }

            @Override
            public void onFail(Call<BaseBean<homeBean>> call, Throwable t) {

            }
        });
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
}
