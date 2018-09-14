package com.zhitou.job.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhitou.job.MyApplication;
import com.zhitou.job.R;
import com.zhitou.job.main.been.ImageBeen;
import com.zhitou.job.main.utils.GlideUtils;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by LCH on 2018/9/14.
 */
public class ImageAdapter extends MyBaseAdapter<ImageBeen>{

    public ImageAdapter(Context context, List<ImageBeen> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image_list,null);
            holder.mIvImage = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageBeen info = data.get(position);
        ViewGroup.LayoutParams params = holder.mIvImage.getLayoutParams();
        params.width = MyApplication.windowwidth;
        params.height = (int)(params.width * Double.parseDouble(info.getRatio()));
        holder.mIvImage.setLayoutParams(params);

        GlideUtils.showPic(context,info.getPath(),holder.mIvImage);

        return convertView;
    }

    class ViewHolder{
        ImageView mIvImage;
    }
}
