package com.zhitou.job.main.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideUtils {
    public static void showPic(Context context, Object imgUrl, ImageView imageView){
        try{
            Glide.with(context)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate().into(imageView);
        }catch (Exception e){
        }
    }

    public static void showPicPlaceholderAndError(Context context, Object imgUrl, int placeholder, int error, ImageView imageView) {

        try{
            Glide.with(context).load(imgUrl)
                    .placeholder(placeholder)
                    .error(error)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .centerCrop()
                    .into(imageView);
        }catch (Exception e){
        }
    }

    public static void showPicPlaceholderAndErrorAndSize(Context context, Object imgUrl, int placeholder, int error, int w, int h, ImageView imageView) {
        try{
            Glide.with(context).load(imgUrl)
                    .placeholder(placeholder)
                    .error(error)
                    .override(w,h)
//                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
                    .into(imageView);
        }catch (Exception e){
        }

    }
}
