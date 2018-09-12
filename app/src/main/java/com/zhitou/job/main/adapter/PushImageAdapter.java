package com.zhitou.job.main.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhitou.job.R;
import com.zhitou.job.main.been.ImageBeen;
import com.zhitou.job.main.utils.GlideUtils;

import java.util.List;

/**
 * Created by LCH on 2018/9/12.
 */
public class PushImageAdapter extends RecyclerView.Adapter<PushImageAdapter.PushImageViewHolder>{

    private OnDeleteListener onDeleteListener;
    protected Context mContext;
    protected List<ImageBeen> data;

    public PushImageAdapter(Context mContext, List<ImageBeen> data,OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public PushImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PushImageViewHolder holder = new PushImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_push_image,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(PushImageViewHolder holder, final int position) {
        GlideUtils.showPic(mContext,data.get(position).getPath(),holder.imageView);

        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                notifyItemRemoved(position);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                        onDeleteListener.onDeleteListener(position);
                    }
                },400);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PushImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvDelete;
        public ImageView imageView;

        public PushImageViewHolder(View itemView) {
            super(itemView);
            //初始化控件
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }

    public interface OnDeleteListener{
        void onDeleteListener(int position);
    }
}
