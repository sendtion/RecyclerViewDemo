package com.fb.recyclerviewdemo;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fb.recyclerviewdemo.util.AppManager;
import com.fb.recyclerviewdemo.util.StatusBarUtil;

/**
 * Description:
 * CreateTime: 2018/6/15 15:18
 * Author: ShengDecheng
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState, @Nullable int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        // 强制竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

//        ButterKnife.bind(this);
//        if (isBindEventBus() && !EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }

        //initStatusBar();
        initView();
        initData();
        loadData();
    }

    protected void initStatusBar() {
        StatusBarUtil.addStatusBarView(this, Color.WHITE, 00);
        StatusBarUtil.setStatusBar(this, false, false);
        StatusBarUtil.setStatusTextColor(true, this);
    }

    protected abstract void loadData();

    protected abstract void initData();

    protected abstract void initView();

    public void showToastShort(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
        System.gc();
    }

//    protected boolean isBindEventBus() {
//        return false;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }
}
