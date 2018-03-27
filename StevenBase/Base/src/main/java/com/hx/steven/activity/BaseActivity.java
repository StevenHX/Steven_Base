package com.hx.steven.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hx.steven.BroadCastReceiver.NetworkChangedReceiver;
import com.hx.steven.R;
import com.hx.steven.component.MProgressDialog;
import com.hx.steven.component.MultipleStatusView;
import com.hx.steven.util.MPermissionUtil;

public abstract class BaseActivity extends SlidingActivity{

    private  static  final int TOP_HEIGHT = 48;
    private boolean isShowHeader = true;//是否显示导航栏(默认显示)

    private ViewGroup mContainer;//视图容器

    private RelativeLayout mHeaderLayout;//导航栏控件
    private TextView mHeaderLeftTv;
    private TextView mHeaderTitleTv;
    private TextView mHeaderRightTv;

    private MultipleStatusView multipleStatusView;//内容多状态视图
    private MProgressDialog mProgressDialog;//dialog
    public static NetworkChangedReceiver.NetEvevt evevt;//网络状态接口对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);//设置屏幕保持竖直
        isShowHeader = isShowHeader();
        initContainer();
        setStatusColor(0x20000000);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void initView();
    protected abstract int getContentId();
    protected  abstract boolean isShowHeader();
    /**
     * 设置状态栏颜色
     */
    private void setStatusColor(int statusColor){
        //操作系统的api版本大于21，才能改变状态栏的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的颜色
            getWindow().setStatusBarColor(statusColor);
        }
    }

    /**
     * 初始化内容视图
     */
    private void initContainer() {
        setContentView(R.layout.activity_base);
        mContainer = (ViewGroup) findViewById(R.id.base_container);
        multipleStatusView = (MultipleStatusView) findViewById(R.id.base_multipleView);
        View layout = null;
        LayoutInflater inflater = LayoutInflater.from(BaseActivity.this);
        layout = inflater.inflate(getContentId(),null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        multipleStatusView.addView(layout);

        if(isShowHeader){
            mHeaderLayout = (RelativeLayout) findViewById(R.id.header_view);
            mHeaderLayout.setVisibility(View.VISIBLE);
            mHeaderLeftTv = (TextView) findViewById(R.id.header_left);
            mHeaderTitleTv = (TextView) findViewById(R.id.header_title);
            mHeaderRightTv = (TextView) findViewById(R.id.header_right);
            View.OnClickListener headerListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = view.getId();
                    if (i == R.id.header_left) {
                        headerLeftClick(view);

                    } else if (i == R.id.header_title) {
                        headerTitleClick(view);

                    } else if (i == R.id.header_right) {
                        headerRightClick(view);

                    }
                }
            };
            mHeaderLeftTv.setOnClickListener(headerListener);
            mHeaderTitleTv.setOnClickListener(headerListener);
            mHeaderRightTv.setOnClickListener(headerListener);
        }
    }
    protected void headerLeftClick(View view) {
        onBackPressed();//（默认是返回，可以重写该方法）
    }

    protected void headerTitleClick(View view) {
    }
    protected void headerRightClick(View view) {
    }
    /**
     * 6.0权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     *   设置公共方法
     */
    public MultipleStatusView getMultipleStatusView(){
        return multipleStatusView;
    }

    public void  setTitle(String title){
        if(isShowHeader){
            setHeaderVisibility(View.VISIBLE);
            mHeaderTitleTv.setText(title);
        }
    }

    public void  setTitle(int id){
        if(isShowHeader){
            setHeaderVisibility(View.VISIBLE);
            mHeaderTitleTv.setText(getString(id));
        }
    }

    public void setLeftIcon(int resId){
        Drawable drawable= getResources().getDrawable(resId);
        if(drawable==null) return ;
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if(isShowHeader) mHeaderLeftTv.setCompoundDrawables(drawable,null,null,null);
    }
    public void hideLeftIcon(){
        if(isShowHeader) mHeaderLeftTv.setVisibility(View.INVISIBLE);
    }

    public void setRightText(String text){
        if(isShowHeader){
            mHeaderRightTv.setVisibility(View.VISIBLE);
            mHeaderRightTv.setText(text);
        }
    }
    public void hideRightText(){
        if(isShowHeader){
            mHeaderRightTv.setVisibility(View.INVISIBLE);
        }
    }

    public void setHeaderVisibility(int visibility) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) multipleStatusView.getChildAt(0).getLayoutParams();
        if (visibility == View.GONE) {
            lp.topMargin = 0;
            isShowHeader = false;
        } else {
            isShowHeader = true;
        }
        mHeaderLayout.setVisibility(visibility);
    }

    /**
     * 显示dialog
     */
    public void showProgressDialog(String msg){
        if(mProgressDialog==null){
            mProgressDialog = new MProgressDialog.Builder(this)
                    .build();
        }
        mProgressDialog.show(msg);
    }

    /**
     * 隐藏dialog
     */
    public void dismissProgressDialog(){
        if(mProgressDialog!=null)
        mProgressDialog.dismiss();
    }

    /**
     * 开启activity
     */
    public void launch(Context mContext, Bundle bundle, Class clazz) {
        Intent mIntent = new Intent(mContext, clazz);
        mIntent.putExtras(bundle);
        launch(mContext, mIntent);
    }

    /**
     * 开启activity
     */
    public void launch(Context mContext, Class clazz) {
        Intent mIntent = new Intent(mContext, clazz);
        launch(mContext, mIntent);
    }

    /**
     * 开启activity
     */
    public void launch(Context mContext, Intent intent) {
        mContext.startActivity(intent);
    }
    /**
     * 开启activity
     */
    public void launch_FLAGS(Context mContext, Class clazz,int FLAGS) {
        Intent intent = new Intent(mContext, clazz);
        intent.addFlags(FLAGS);
        mContext.startActivity(intent);
    }
    /**
     * 打开activity并且获取回调
     */
    public void  launchForResult(Activity activity, Bundle bundle, Class clazz, int requestCode){
        Intent mIntent = new Intent(activity,clazz);
        mIntent.putExtras(bundle);
        activity.startActivityForResult(mIntent,requestCode);
    }
    /**
     * 打开activity并且获取回调
     */
    public void  launchForResult_FLAGS(Activity activity, Bundle bundle, Class clazz, int requestCode,int FLAGS){
        Intent mIntent = new Intent(activity,clazz);
        mIntent.putExtras(bundle);
        mIntent.addFlags(FLAGS);
        activity.startActivityForResult(mIntent,requestCode);
    }
}
