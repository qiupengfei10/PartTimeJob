package com.zhitou.job.main.utils;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by yinyanyang on 2018/1/12.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, final ImageView imageView) {
        //Glide 加载图片简单用法
//        Glide.with(context).load((String) path).into(imageView);
        GlideUtils.showPic(context,path,imageView);
    }
}
