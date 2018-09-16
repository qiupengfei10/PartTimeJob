package com.zhitou.job.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.main.been.Comment;
import com.zhitou.job.main.been.CommentForTwoHand;
import com.zhitou.job.main.utils.GlideUtils;
import com.zhitou.job.main.utils.TimeUtils;
import com.zhitou.job.main.view.NoScrollListView;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReturnCommentAdapter extends MyBaseAdapter<Comment>{
    public ReturnCommentAdapter(Context context, ArrayList<Comment> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_return_comment,null);
            holder.mIvUserImage = (ImageView) convertView.findViewById(R.id.iv_user_logo);
            holder.mTvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.mTvTime = (TextView) convertView.findViewById(R.id.tv_create_time);
            holder.mTvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment info = data.get(position);



        GlideUtils.showPic(context,info.getUser().getUserImage(),holder.mIvUserImage);
        holder.mTvUserName.setText(info.getUser().getNickName());
//        holder.mTvTime.setText(TimeUtils.getTimeFormatText(info.getCreatedAt()));
        holder.mTvContent.setText(info.getContent());


        return convertView;
    }

    class ViewHolder{
        ImageView mIvUserImage;
        TextView mTvUserName;
        TextView mTvTime;
        TextView mTvContent;
    }


}
