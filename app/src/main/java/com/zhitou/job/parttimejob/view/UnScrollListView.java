package com.zhitou.job.parttimejob.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by qiupengfei on 2017/6/19.
 *
 * 不能滑动的listView 用于listview和listview的嵌套 listview和scrollview的嵌套
 */
public class UnScrollListView extends ListView{
    public UnScrollListView(Context context) {
        super(context);
    }
    public UnScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public UnScrollListView(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    /**
     * 重写该方法、达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
