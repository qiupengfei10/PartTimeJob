package com.zhitou.job.parttimejob.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.zhitou.job.parttimejob.activity.ShopMainActivity;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.R;
import com.zhitou.job.parttimejob.been.HomeShop;
import com.zhitou.job.parttimejob.been.ShopDiscounts;
import com.zhitou.job.parttimejob.view.UnScrollListView;

import java.util.ArrayList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        HomeShop info = data.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_shop,null);
            holder = new ViewHolder();
            holder.mIvShopLogo = (ImageView) convertView.findViewById(R.id.iv_shop_logo);
            holder.mTvShopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.mTvSale = (TextView) convertView.findViewById(R.id.tv_sale_main);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.mTvPostage = (TextView) convertView.findViewById(R.id.tv_postage);
            holder.mTvMinPrice = (TextView) convertView.findViewById(R.id.tv_sale);
            holder.mTvDiscountsNum = (TextView) convertView.findViewById(R.id.tv_discounts_num);
            holder.ulvDiscounts = (UnScrollListView) convertView.findViewById(R.id.ulv_favorable);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


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
        //优惠活动
        final List<ShopDiscounts> discountsList = info.getDiscountsList();

        if (discountsList != null){
            holder.ulvDiscounts.setVisibility(View.VISIBLE);
            List<ShopDiscounts> discountsList1 = new ArrayList<>();
            if (discountsList.size() > 2){
                discountsList1.add(discountsList.get(0));
                discountsList1.add(discountsList.get(1));
                holder.mTvDiscountsNum.setVisibility(View.VISIBLE);
                holder.mTvDiscountsNum.setText(discountsList.size() + "个活动");
            }else {
                discountsList1 = discountsList;
                holder.mTvDiscountsNum.setVisibility(View.GONE);
            }
            holder.ulvDiscounts.setAdapter(new DiscountsAdapter(context,discountsList1));
        }else {
//            holder.mLlFavorable.setVisibility(View.GONE);
            holder.ulvDiscounts.setVisibility(View.GONE);
            holder.mTvDiscountsNum.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShopMainActivity.class);
                intent.putExtra("shop",data.get(position));
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder {
        private ImageView mIvShopLogo; //商店的logo
        private TextView mTvShopName;//商店名称
        private TextView mTvSale;//月销量
        private RatingBar ratingBar;//月销量
        private TextView mTvPostage;//月销量
        public TextView mTvMinPrice;

        private UnScrollListView ulvDiscounts;
        private TextView mTvDiscountsNum;
    }


}
