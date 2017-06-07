package com.yoryky.picmaxview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoryky.picmaxview.R;
import com.yoryky.picmaxview.activity.ImageViewActivity;
import com.yoryky.picmaxview.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoryky on 2017/6/6.
 */

public class MinPicAdapter extends RecyclerView.Adapter<MinPicAdapter.MinViewHolder> {
    private Context mContext;
    private List<String> mItems = new ArrayList<>();
    private ArrayList<String> mMaxPics = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public MinPicAdapter(Context context, List<ImageEntity> items) {
        this.mContext = context;
        fillItems(items);
    }

    private void fillItems(List<ImageEntity> items){
        for(int i = 0;i< items.size();i++){
            this.mItems.add(items.get(i).getMin_url());
            this.mMaxPics.add(items.get(i).getMax_url());
        }
    }

    @Override
    public MinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MinViewHolder holder = new MinViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pic_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MinViewHolder holder, final int position) {
        Picasso.with(mContext).load(mItems.get(position)).placeholder(R.mipmap.ic_launcher).into(holder.ivPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ImageViewActivity.class);
                intent.putStringArrayListExtra("max_pics",mMaxPics);
                intent.putExtra("index",position);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class MinViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;

        public MinViewHolder(View view) {
            super(view);
            ivPic = (ImageView) view.findViewById(R.id.iv_pic);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
