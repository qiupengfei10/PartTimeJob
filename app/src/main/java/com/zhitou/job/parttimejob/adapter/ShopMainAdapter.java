package com.zhitou.job.parttimejob.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.been.ProductClassify;
import com.zhitou.job.parttimejob.view.UnScrollListView;

import java.util.List;

/**
 * Created by qiupengfei on 2017/6/26.
 */
public class ShopMainAdapter extends MyBaseAdapter<ProductClassify> {

    private Handler handler;

    public ShopMainAdapter(Context context, List<ProductClassify> data,Handler handler) {
        super(context, data);
        this.handler = handler;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_main,null);
            holder = new ViewHolder();
            holder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mUlvProduct = (UnScrollListView) convertView.findViewById(R.id.ulv_product);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.mUlvProduct.setAdapter(new ProductAdapter(context,data.get(position).getProducts(),handler));
        holder.mTvTitle.setText(data.get(position).getSubject());

        return convertView;
}

    class ViewHolder {
        TextView mTvTitle;
        UnScrollListView mUlvProduct;
    }
}
