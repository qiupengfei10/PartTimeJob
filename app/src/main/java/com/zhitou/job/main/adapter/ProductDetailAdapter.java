package com.zhitou.job.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhitou.job.MyApplication;
import com.zhitou.job.R;
import com.zhitou.job.main.utils.GlideUtils;

import java.util.List;



/**
 * Created by Administrator on 2017/10/9.
 */
public class ProductDetailAdapter extends BaseAdapter{

    private final Context mContext;
    private final List<String> data;

    public ProductDetailAdapter(Context context, List<String> showPicture){
        this.mContext = context;
        this.data = showPicture;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        String info = data.get(position);

        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_picture_img,null);
            holder.iv = (ImageView) convertView.findViewById(R.id.item_picture_img_iv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ViewGroup.LayoutParams params = holder.iv.getLayoutParams();
        params.height = (int)(MyApplication.windowwidth);
        params.width = MyApplication.windowwidth;
        holder.iv.setLayoutParams(params);
        holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Log.e("qpf","holder.iv.getTag() --->  " + holder.iv.getTag());
        if (holder != null)
//            Glide.with(mContext).load(info.getPath()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into(holder.iv);
//        Glide.with(mContext)
//                .load(info.getPath())
//                .override(params.width,params.height) // resizes the image to these dimensions (in pixel)
//                .centerCrop() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
//                .into(holder.iv);

        GlideUtils.showPicPlaceholderAndErrorAndSize(mContext,info, R.mipmap.default_image, R.mipmap.default_image,params.width,params.height,holder.iv);

        return convertView;
    }

    class ViewHolder{
        ImageView iv;
    }
}