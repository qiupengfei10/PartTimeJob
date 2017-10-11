package com.zhitou.job.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.been.HomeShop;

import java.util.List;

/**
 * Created by qiupengfei on 2017/6/22.
 */
public class HomeShopAdapter extends MyBaseAdapter<HomeShop> {
    public HomeShopAdapter(Context context, List<HomeShop> data) {
        super(context, data);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_shop,null);
            holder = new ViewHolder();
            holder.mIvShopLogo = (ImageView) convertView.findViewById(R.id.iv_shop_logo);
            holder.mTvShopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.mTvSale = (TextView) convertView.findViewById(R.id.tv_sale_main);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.mTvPostage = (TextView) convertView.findViewById(R.id.tv_postage);
            holder.mTvMinPrice = (TextView) convertView.findViewById(R.id.tv_sale);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeShop info = data.get(position);

        //店名
        holder.mTvShopName.setText(info.getShopName());
        //商店logo
        Glide.with(context).load(info.getShop_logo()).into(holder.mIvShopLogo);
        //月销量
        int sale = info.getSale() != null ? Integer.valueOf(info.getSale()) : 0;
        holder.mTvSale.setText("月销量"+sale+"件");
        //好评
        int reputation = info.getReputation() != null ? Integer.valueOf(info.getReputation()) : 8;
        holder.ratingBar.setProgress(reputation);
        //最低消费(多少钱起送)
        int minPrice = info.getPostage() != null ? Integer.valueOf(info.getMininum_consume()) : 0;
        holder.mTvMinPrice.setText("￥" + minPrice + "起送");
        //配送费
        int postage = info.getPostage() != null ? Integer.valueOf(info.getPostage()) : 0;
        holder.mTvPostage.setText(postage == 0 ? "免配送费":"配送费" + postage + "元");


        return convertView;
    }

    class ViewHolder {
        private ImageView mIvShopLogo; //商店的logo
        private TextView mTvShopName;//商店名称
        private TextView mTvSale;//月销量
        private RatingBar ratingBar;//月销量
        private TextView mTvPostage;//月销量
        public TextView mTvMinPrice;
    }
}
