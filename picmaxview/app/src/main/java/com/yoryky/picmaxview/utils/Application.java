package com.yoryky.picmaxview.utils;

import com.yoryky.picmaxview.entity.Constants;

import cn.bmob.v3.Bmob;

/**
 * Created by Yoryky on 2017/6/6.
 */

public class Application extends android.app.Application {
    private static Application newInstance;
    public static Application getInstance(){
        return newInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        newInstance = this;
        this.initBmob();
    }

    private void initBmob(){
        Bmob.initialize(this, Constants.BMOB_APPID);
    }
}
