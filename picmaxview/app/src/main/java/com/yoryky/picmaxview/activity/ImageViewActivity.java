package com.yoryky.picmaxview.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yoryky.picmaxview.R;
import com.yoryky.picmaxview.listener.ImageGestureListener;
import com.yoryky.picmaxview.utils.AsynImageLoader;
import com.yoryky.picmaxview.utils.ImageUtil;
import com.yoryky.picmaxview.widget.BaseZoomableImageView;

import java.util.List;

/**
 * Created by Yoryky on 2017/6/7.
 */

public class ImageViewActivity extends Activity {
    private PagerAdapter mPagerAdapter;
    private List<String> mMaxPics;
    private int mCurrentIndex;
    private View mLoadingLayout;
    private ViewPager mViewPager;
    private boolean mNewPageSelected = false;
    private BaseZoomableImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actvitiy_imageview);
        this.initViews();
    }

    private void initViews(){
        mMaxPics = getIntent().getStringArrayListExtra("max_pics");
        mCurrentIndex =  getIntent().getIntExtra("index",0);
        mViewPager = (ViewPager)findViewById(R.id.vp_image);
        mLoadingLayout = findViewById(R.id.loading_layout);
        setViewPagerAdapter();
    }

    private void setViewPagerAdapter(){
        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mMaxPics.size();
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View layout = (View)object;
                BaseZoomableImageView iv_image = (BaseZoomableImageView)layout.findViewById(R.id.ziv_image);
                iv_image.clear();
                container.removeView(layout);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (view == object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ViewGroup layout = (ViewGroup) LayoutInflater.from(ImageViewActivity.this).inflate(R.layout.image_layout,null);
                layout.setBackgroundColor(Color.BLACK);
                container.addView(layout);
                layout.setTag(position);
                if(position == mCurrentIndex){
                    onViewPagerSelected(position);
                }
                return layout;
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(mCurrentIndex);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(positionOffset == 0f && mNewPageSelected){
                    mNewPageSelected = false;
                    onViewPagerSelected(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mNewPageSelected = true;
                mCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void onViewPagerSelected(int position){
        updateCurrentImageView(position);
        onImageViewFound(mImageView);
        setImageView();
    }

    private void setImageView(){
        mLoadingLayout.setVisibility(View.VISIBLE);
        mImageView.setTag(mMaxPics.get(mCurrentIndex));
        AsynImageLoader.getInstance().loadImageAsync(mMaxPics.get(mCurrentIndex),new AsynImageLoader.ImageCallback(){
            @Override
            public void loadImage(String path, Bitmap bitmap) {
                if(path.equals(mImageView.getTag().toString()) && bitmap != null){
                    mImageView.setImageBitmap(bitmap);
                }else{
                    mImageView.setImageBitmap(ImageUtil.getBitmapFromDrawableRes(getResources(),getImageResFailed()));
                }
                mLoadingLayout.setVisibility(View.GONE);
            }
        });
    }

    private void updateCurrentImageView(final int position){
        View currentLayout = mViewPager.findViewWithTag(position);
        if(currentLayout == null){
            ViewCompat.postOnAnimation(mViewPager, new Runnable() {
                @Override
                public void run() {
                    updateCurrentImageView(position);
                }
            });
            return;
        }
        mImageView = (BaseZoomableImageView)currentLayout.findViewById(R.id.ziv_image);
    }
    private int getImageResFailed(){
        return R.mipmap.download_failed;
    }

    //单击图片
    private void onImageViewTouched(){
        finish();
    }
    //长按图片
    private  void showWatchPictureAction(){
        //
    }

    protected  void onImageViewFound(BaseZoomableImageView view){
        view.setImageGestureListener(new ImageGestureListener() {
            @Override
            public void onImageGestureSingleTapConfirmed() {
                onImageViewTouched();
            }

            @Override
            public void onImageGestureLongPress() {
                showWatchPictureAction();
            }

            @Override
            public void onImageGestureFlingDown() {
                finish();
            }
        });
    }
}
