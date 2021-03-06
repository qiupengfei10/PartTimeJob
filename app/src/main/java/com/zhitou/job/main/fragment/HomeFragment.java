package com.zhitou.job.main.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhitou.job.R;
import com.zhitou.job.chat.ui.SplashActivity;
import com.zhitou.job.main.activity.TwoHandDetailActivity;
import com.zhitou.job.main.adapter.TwoHandAdapter;
import com.zhitou.job.main.base.BaseFragment;
import com.zhitou.job.main.been.TwoHand;
import com.zhitou.job.main.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeFragment extends BaseFragment{
    int [] colors = new int[4];
    private NoScrollListView listView;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private int size = 10;

    private List<TwoHand> twoHands = new ArrayList<>();
    private List<TwoHand> newTwoHands = new ArrayList<>();
    private BaseAdapter adapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpView() {
        listView = (NoScrollListView) getContentView().findViewById(R.id.listview);
        refreshLayout = (SmartRefreshLayout) getContentView().findViewById(R.id.refreshLayout);
        colors = new int[]{R.color.blue_color,R.color.colorAccent,R.color.colorPrimary,R.color.main_color};

//        refreshLayout.setEnableLoadMore(false);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getTwoHandList(page);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getTwoHandList(page);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TwoHandDetailActivity.class);
                intent.putExtra("twoHand",twoHands.get(position));
                startActivity(intent);
            }
        });

        getContentView().findViewById(R.id.btn_go_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SplashActivity.class));
            }
        });
    }

    @Override
    protected void setUpData() {
        page = 1;
        showLoading();
        getTwoHandList(page);
    }

    public void getTwoHandList(final int page) {
        showLoading();
        BmobQuery<TwoHand> query = new BmobQuery<>();
        query.include("PushUser");// 需要返回用户信息
        query.order("-createdAt");
        query.setLimit(size);

        query.setSkip((page - 1) * size);
        query.findObjects(new FindListener<TwoHand>() {
            @Override
            public void done(List<TwoHand> list, BmobException e) {
                if (e == null){
                    L("qpf",list.size()+"");
                    newTwoHands = list;
                    if (page == 1){  //第一页
                        twoHands.clear();
                        refreshLayout.setEnableLoadMore(true);
                    }else {
                        if (newTwoHands.size() == 0){
                            Toast.makeText(getMContext(),"无更多数据！",Toast.LENGTH_SHORT).show();
                            refreshLayout.setEnableLoadMore(false);
                        }
                    }
                    //多页
                    twoHands.addAll(newTwoHands);

                    if (adapter == null){
                        adapter = new TwoHandAdapter(getMContext(),twoHands);
                        listView.setAdapter(adapter);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    L("qpf","error -- " + e.toString());
                }

                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
            }
        });
    }
}
