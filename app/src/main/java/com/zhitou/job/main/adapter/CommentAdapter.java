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

import java.util.List;

public class CommentAdapter extends MyBaseAdapter<CommentForTwoHand>{
    private OnItemClickListener onItemClickListener;

    public CommentAdapter(Context context, List<CommentForTwoHand> data, OnItemClickListener onItemClickListener) {
        super(context, data);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment,null);
            holder.mIvUserImage = (ImageView) convertView.findViewById(R.id.iv_user_logo);
            holder.mTvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.mTvTime = (TextView) convertView.findViewById(R.id.tv_create_time);
            holder.mTvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.mLvReturnComment = (NoScrollListView) convertView.findViewById(R.id.lv_return_comment);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CommentForTwoHand info = data.get(position);



        GlideUtils.showPic(context,info.getUser().getUserImage(),holder.mIvUserImage);
        holder.mTvUserName.setText(info.getUser().getNickName());
        holder.mTvTime.setText(TimeUtils.getTimeFormatText(info.getCreatedAt()));
        holder.mTvContent.setText(info.getContent());

        if (info.getReturnCommnet()!=null&&info.getReturnCommnet().size()>0){
            holder.mLvReturnComment.setAdapter(new ReturnCommentAdapter(context,info.getReturnCommnet()));
            holder.mLvReturnComment.setVisibility(View.VISIBLE);
        }else {
            holder.mLvReturnComment.setVisibility(View.GONE);
        }

        holder.mLvReturnComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onItemClickListener.onItemClickListener(info,info.getReturnCommnet().get(i));
            }
        });

        return convertView;
    }

    class ViewHolder{
        ImageView mIvUserImage;
        TextView mTvUserName;
        TextView mTvTime;
        TextView mTvContent;

        NoScrollListView mLvReturnComment;
    }

    public interface OnItemClickListener{
        void onItemClickListener(CommentForTwoHand commentForTwoHand,Comment comment);
    }


}
