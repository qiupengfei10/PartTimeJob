package com.zhitou.job.parttimejob.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.activity.PushProductInfoActivity;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.parttimejob.been.Product;
import com.zhitou.job.parttimejob.constant.Constant;

import java.util.List;

public class ProductManageAdapter extends MyBaseAdapter<Product> {
    public ProductManageAdapter(Context context, List<Product> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_manage,null);
            holder = new ViewHolder();
            holder.mIvProductImage = (ImageView) convertView.findViewById(R.id.iv_product_image);
            holder.mTvProductName = (TextView) convertView.findViewById(R.id.tv_product_title);
            holder.mTvProductPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.mTvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            holder.mTvEdt = (TextView) convertView.findViewById(R.id.tv_edt_product);
            holder.mTvDown = (TextView) convertView.findViewById(R.id.tv_down_product);
            holder.mTvDelete = (TextView) convertView.findViewById(R.id.tv_delete_product);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Product info = data.get(position);
        Glide.with(context).load(info.getImage_url()).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.mIvProductImage);
        holder.mTvProductName.setText(info.getName());
        holder.mTvProductPrice.setText("￥" + info.getPrice());

        if (info.getStatus() != null)
            holder.mTvStatus.setText(info.getStatus());
        else
            holder.mTvStatus.setVisibility(View.GONE);

        holder.mTvEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //编辑商品
                Intent intent = new Intent(context, PushProductInfoActivity.class);
                intent.putExtra("from", Constant.PRODUCT_MANAGE_EDT);
                intent.putExtra("product",info);
                intent.putExtra("shop_id",info.getShop_id());
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder{
        private ImageView mIvProductImage;
        private TextView mTvProductName;
        private TextView mTvProductPrice;
        private TextView mTvStatus;//简介
        private TextView mTvEdt;//编辑
        private TextView mTvDown;//下架
        private TextView mTvDelete;//删除

    }
}
