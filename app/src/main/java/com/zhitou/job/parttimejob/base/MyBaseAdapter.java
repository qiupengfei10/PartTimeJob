package com.zhitou.job.parttimejob.base;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by qiupengfei on 2017/6/22.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{
    protected Context context;
    protected List<T> data;

    public MyBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    protected void L(String tag,String content){
        Log.e(context.getPackageName(),"【"+tag+"】"+content);
    }
}
