package com.yoryky.picmaxview.entity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by Yoryky on 2017/6/6.
 */

public class ImageEntity extends BmobObject {
    private String max_url;
    private String min_url;
    private String comment;

    public String getMax_url() {
        return max_url;
    }

    public void setMax_url(String max_url) {
        this.max_url = max_url;
    }

    public String getMin_url() {
        return min_url;
    }

    public void setMin_url(String min_url) {
        this.min_url = min_url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
