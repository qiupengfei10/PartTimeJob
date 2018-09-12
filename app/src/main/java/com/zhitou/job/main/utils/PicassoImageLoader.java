package com.zhitou.job.main.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.zhitou.job.R;

import java.io.File;


/**
 * Created by yinyanyang on 2018/3/21.
 *
 * 图片选择器
 */
public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

        GlideUtils.showPicPlaceholderAndError(activity,Uri.fromFile(new File(path)), R.mipmap.default_image, R.mipmap.default_image,imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}