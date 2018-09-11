package com.zhitou.job.main.fragment;

import android.widget.ListView;

import com.zhitou.job.main.adapter.TwoHandAdapter;
import com.zhitou.job.main.base.BaseFragment;
import com.zhitou.job.main.been.TwoHand;
import com.zhitou.job.main.view.NoScrollListView;
import com.zhitou.job.parttimejob.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeFragment extends BaseFragment{
    int [] colors = new int[4];
    private NoScrollListView listView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpView() {
        listView = (NoScrollListView) getContentView().findViewById(R.id.listview);
        colors = new int[]{R.color.blue_color,R.color.colorAccent,R.color.colorPrimary,R.color.main_color};


    }

    @Override
    protected void setUpData() {
        getTwoHandList();
    }

    public void getTwoHandList() {
        BmobQuery<TwoHand> query = new BmobQuery<>();
        query.findObjects(new FindListener<TwoHand>() {
            @Override
            public void done(List<TwoHand> list, BmobException e) {
                if (e == null){
                    L("qpf",list.size()+"");
                    listView.setAdapter(new TwoHandAdapter(getMContext(),list));
                }else {
                    L("qpf","error -- " + e.toString());
                }
            }
        });
    }
}
