package com.zhitou.job.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.parttimejob.been.ProductClassify;

import java.util.List;

/**
 * Created by qiupengfei on 2017/6/23.
 */
public class SubAdapter extends MyBaseAdapter<ProductClassify> {
    public SubAdapter(Context context, List<ProductClassify> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sub,null);
            holder = new ViewHolder();
            holder.mTvSub = (TextView) convertView.findViewById(R.id.tv_sub);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductClassify info = data.get(position);

        holder.mTvSub.setText(info.getSubject());
        return convertView;
    }

    class ViewHolder{
        TextView mTvSub;
    }
}
