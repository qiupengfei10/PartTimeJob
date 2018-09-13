package com.zhitou.job.main.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhitou.job.R;
import com.zhitou.job.main.adapter.TaoBaoProductAdapter;
import com.zhitou.job.main.been.TaoBaoBeen;
import com.zhitou.job.main.constant.Constant;
import com.zhitou.job.main.inter.ResultListener;
import com.zhitou.job.main.utils.BaseOkGoUtils;
import com.zhitou.job.main.utils.CommonUtils;
import com.zhitou.job.main.utils.JsonUtils;
import com.zhitou.job.main.view.NoScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by LCH on 2018/9/13.
 */
@SuppressLint("ValidFragment")
public class TaoBaoProductListFragment extends Fragment{

    private View view;
    private NoScrollListView mLvProduct;
    SmartRefreshLayout refreshLayout;

    private String currentName;
    private TaoBaoBeen taobaoBeen;
    private int pageSize = 20;
    private int pageIndex = 1;
    private List<TaoBaoBeen.GoodsBean> newGoodsList = new ArrayList<>();
    private List<TaoBaoBeen.GoodsBean> GoodsList = new ArrayList<>();
    private TaoBaoProductAdapter ProductAdapter;


    public TaoBaoProductListFragment(String currentName) {
        this.currentName = currentName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_product_list,null);
        initView();
        if (GoodsList == null || GoodsList.size() == 0)
        initData(pageIndex);
        return view;
    }

    private void initView() {
        mLvProduct = (NoScrollListView)view.findViewById(R.id.lv_product);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.clothes_list_smartRefreshLayout);

        mLvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDetailNew(GoodsList.get(i).getNumIid(),GoodsList.get(i).getNick());
            }
        });

        ProductAdapter = new TaoBaoProductAdapter(getActivity(),GoodsList);
        mLvProduct.setAdapter(ProductAdapter);


        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                initData(pageIndex);
            }
        });

        //上拉加载更多
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                initData(pageIndex);
            }
        });
    }

    private void showDetailNew(String numIid,String shopName) {
//        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
//        intent.putExtra("itemId",numIid);
//        intent.putExtra("shopName",shopName);
//        startActivity(intent);
    }

    @Override
    public void onDestroy() {
//        AlibcTradeSDK.destory();
        super.onDestroy();
    }

    public void setSearch(String key){
        currentName = key;
        pageIndex = 1;
        initData(pageIndex);
    }


    private void initData(final int pageIndex) {
        Map<String, Object> mapProduct = new HashMap<>();
        mapProduct.put("key",currentName);
        mapProduct.put("pageIndex",pageIndex);
        mapProduct.put("pageSize",pageSize);
        BaseOkGoUtils.getOkGo(mapProduct, Constant.GET_TAOBAO_PTODUCT,getActivity(), new ResultListener() {
            @Override
            public void onSucceeded(Object object) {

                taobaoBeen = JsonUtils.parseJsonWithGson(object, TaoBaoBeen.class);
                newGoodsList = taobaoBeen.getGoods();
                if (pageIndex == 1){
                    GoodsList.clear();
                    GoodsList.addAll(newGoodsList);
                    refreshLayout.finishRefresh();
                }else if (pageIndex > 1){
                    if (newGoodsList.size() == 0){
                        CommonUtils.showToast("无更多数据！",getActivity());
                        refreshLayout.setEnableLoadMore(false);
                    }else {
                        GoodsList.addAll(newGoodsList);
                    }
                    refreshLayout.finishLoadMore();
                }
                //过去
                ProductAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String content) {

            }

            @Override
            public void onErr(String e) {

            }
        });
    }
}
