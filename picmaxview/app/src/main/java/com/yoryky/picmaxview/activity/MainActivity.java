package com.yoryky.picmaxview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yoryky.picmaxview.R;
import com.yoryky.picmaxview.adapter.MinPicAdapter;
import com.yoryky.picmaxview.entity.ImageEntity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Yoryky on 2017/5/25.
 */

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private List<ImageEntity> mImages;
    private MinPicAdapter mMinPicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initData();
    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_main);
        mMinPicAdapter = new MinPicAdapter(this,mImages);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        //mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mMinPicAdapter);
    }

    private void initData(){
        BmobQuery<ImageEntity> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<ImageEntity>() {
            @Override
            public void done(List<ImageEntity> list, BmobException e) {
                mImages = list;
                initViews();
            }
        });
    }
}
