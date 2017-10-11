package com.zhitou.job.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.parttimejob.R;

import java.util.List;

/**
 * Created by qiupengfei on 2017/6/23.
 */
public class SubAdapter extends MyBaseAdapter<String> {
    public SubAdapter(Context context, List<String> data) {
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
        holder.mTvSub.setText(data.get(position));
        return convertView;
    }

    class ViewHolder{
        TextView mTvSub;
    }
}
