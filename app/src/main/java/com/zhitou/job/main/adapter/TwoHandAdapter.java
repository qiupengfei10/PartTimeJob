package com.zhitou.job.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhitou.job.R;
import com.zhitou.job.main.been.TwoHand;
import com.zhitou.job.main.utils.TimeUtils;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;

import java.util.List;

public class TwoHandAdapter extends MyBaseAdapter<TwoHand> {


    public TwoHandAdapter(Context context, List<TwoHand> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_es_product,null);

            holder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mTvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.mTvAddress = (TextView) convertView.findViewById(R.id.tv_address);
            holder.image1 = (ImageView) convertView.findViewById(R.id.iv_image1);
            holder.image2 = (ImageView) convertView.findViewById(R.id.iv_image2);
            holder.image3 = (ImageView) convertView.findViewById(R.id.iv_image3);
            holder.mIvUserLogo = (ImageView) convertView.findViewById(R.id.iv_user_logo);
            holder.mTvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.mTvCreateTime = (TextView) convertView.findViewById(R.id.tv_create_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        TwoHand info = data.get(position);

        holder.mTvTitle.setText(info.getTitle());
        holder.mTvAddress.setText(info.getAddress());
        holder.mTvPrice.setText(info.getPrice());

        String[] images = info.getImages().split(";");

        if (images.length >= 3){
            Glide.with(context).load(images[0]).dontAnimate().into(holder.image1);
            Glide.with(context).load(images[1]).dontAnimate().into(holder.image2);
            Glide.with(context).load(images[2]).dontAnimate().into(holder.image3);
        }else {
            L("qpf","images -- " + images.length + " --- " + info.getImages());
        }

        holder.mTvCreateTime.setText(TimeUtils.getTimeFormatText(info.getCreatedAt()));

        //将格式化时间

        if (info.getPushUser() != null)
            holder.mTvUserName.setText(info.getPushUser().getNickName());
        if (info.getPushUser() != null){
            Glide.with(context).load(info.getPushUser().getUserImage()).dontAnimate().into(holder.mIvUserLogo);
            L("qpf","name -- " + info.getPushUser().getObjectId()+
                    " --- " + info.getPushUser().getCreatedAt() + " --- "+ info.getPushUser().toString());
        }

        return convertView;
    }
    class ViewHolder{
        private TextView mTvTitle;
        private TextView mTvAddress;
        private TextView mTvPrice;
        private ImageView image1;
        private ImageView image2;
        private ImageView image3;

        private TextView mTvUserName;
        private ImageView mIvUserLogo;
        private TextView mTvCreateTime;
    }
}
