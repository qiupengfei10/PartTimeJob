package com.zhitou.job.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.parttimejob.been.ShopDiscounts;

import java.util.List;

/**
 * Created by qiupengfei on 2017/10/12.
 */
public class DiscountsAdapter extends MyBaseAdapter<ShopDiscounts> {
    public DiscountsAdapter(Context context, List<ShopDiscounts> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_favorable,null);
            holder = new ViewHolder();
            holder.mTvKeyWord = (TextView) convertView.findViewById(R.id.tv_key_word);
            holder.mTvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopDiscounts info = data.get(position);
        switch (info.getType()){
            case 0:
                holder.mTvKeyWord.setBackgroundResource(R.drawable.shape_main_yollow);
                break;
            case 1:
                holder.mTvKeyWord.setBackgroundResource(R.drawable.shape_main_green);
                break;
            case 2:
                holder.mTvKeyWord.setBackgroundResource(R.drawable.shape_main_blue);
                break;
        }

        holder.mTvKeyWord.setText(data.get(position).getKey_word());
        holder.mTvStatus.setText(data.get(position).getStatus());

        return convertView;
    }

    class ViewHolder{
        private TextView mTvKeyWord;
        private TextView mTvStatus;

    }
}
