package com.zhitou.job.parttimejob.adapter;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.been.Point;
import com.zhitou.job.parttimejob.been.Product;

import java.util.List;

/**
 * Created by qiupengfei on 2017/6/23.
 */
public class ProductAdapter extends MyBaseAdapter<Product> {

    private Handler handler;

    public ProductAdapter(Context context, List<Product> data) {
        super(context, data);
    }
    public ProductAdapter(Context context, List<Product> data, Handler handler) {
        super(context, data);
        this.handler = handler;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product,null);
            holder = new ViewHolder();
            holder.mIvAdd = (ImageView) convertView.findViewById(R.id.iv_add_shop_bus);
            holder.mIvProductImage = (ImageView) convertView.findViewById(R.id.iv_product_image);
            holder.mTvProductName = (TextView) convertView.findViewById(R.id.tv_product_title);
            holder.mTvProductPrice = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product info = data.get(position);
        Glide.with(context).load(info.getImage_url()).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.mIvProductImage);
        holder.mTvProductName.setText(info.getName());
        holder.mTvProductPrice.setText("￥" + info.getPrice());

        holder.mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                int[] fs = new int[2];
                holder.mIvAdd.getLocationOnScreen(fs);
                msg.what = 0;
                msg.arg1 = position;//第几个商品
//                msg.arg1 = holder.mIvAdd.getWidth(); //x轴
//                msg.arg2 = holder.mIvAdd.getHeight(); //y轴
//                msg.obj = new Point(fs[0],fs[1]);
                handler.sendMessage(msg);
//                Log.e("qpf","加号的位置坐标 (" + fs[0] + "," + fs[1] + ")");
            }
        });

        return convertView;
    }

    class ViewHolder{
        private ImageView mIvAdd;
        private ImageView mIvProductImage;
        private TextView mTvProductName;
        private TextView mTvProductPrice;
    }
}
