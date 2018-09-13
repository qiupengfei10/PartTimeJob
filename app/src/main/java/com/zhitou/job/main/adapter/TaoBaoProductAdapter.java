package com.zhitou.job.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.main.been.TaoBaoBeen;
import com.zhitou.job.main.utils.GlideUtils;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;

import java.util.List;


public class TaoBaoProductAdapter extends MyBaseAdapter<TaoBaoBeen.GoodsBean> {
    public TaoBaoProductAdapter(Context mContext, List<TaoBaoBeen.GoodsBean> data) {
        super(mContext, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_taobo_product,null);
            holder = new ViewHolder();
            holder.mIvProduct = (ImageView) convertView.findViewById(R.id.iv_product_image);
            holder.mTvProductName = (TextView) convertView.findViewById(R.id.tv_product_title);
            holder.mTvProductPrice = (TextView) convertView.findViewById(R.id.tv_product_price);
            holder.mTvProductAddress = (TextView) convertView.findViewById(R.id.tv_product_address);
            holder.mTvNum = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        TaoBaoBeen.GoodsBean info = data.get(position);

        GlideUtils.showPicPlaceholderAndError(context,info.getPictUrl(), R.mipmap.default_image, R.mipmap.default_image,holder.mIvProduct);
//        Glide.with(mContext).load(info.getPictUrl()).placeholder(R.mipmap.default_image).dontAnimate().into(holder.mIvProduct);

        holder.mTvProductName.setText(""+info.getTitle());

        holder.mTvProductPrice.setText("¥"+info.getZkFinalPrice());

        holder.mTvProductAddress.setText(info.getProvcity());

        holder.mTvNum.setText("销量："+info.getVolume());

        return convertView;
    }

    class ViewHolder{
        ImageView mIvProduct;
        TextView mTvProductName;
        TextView mTvProductPrice;
        TextView mTvProductAddress;
        TextView mTvNum;
    }
}
