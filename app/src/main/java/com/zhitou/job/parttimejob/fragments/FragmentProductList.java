package com.zhitou.job.parttimejob.fragments;

import android.annotation.SuppressLint;
import android.util.Log;

import com.zhitou.job.parttimejob.adapter.ProductAdapter;
import com.zhitou.job.parttimejob.base.BaseFragmentList;
import com.zhitou.job.parttimejob.base.MyBaseAdapter;
import com.zhitou.job.parttimejob.been.Product;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by qiupengfei on 2017/10/13.
 */
@SuppressLint("ValidFragment")
public class FragmentProductList extends BaseFragmentList<Product>{

    public FragmentProductList(String key,String value) {
        super.key = key;
        super.value = value;
    }

    protected void initData(){
        Log.e("qpf","加载数据");
        BmobQuery<Product> query = new BmobQuery<>();
        if (key !=null && value != null){
            query.addWhereEqualTo(key,value);
        }
        query.findObjects(new FindListener<Product>() {
            @Override
            public void done(List<Product> list, BmobException e) {
                if (e == null){
                    Log.e("qpf","加载数据成功" + list.size());
                    data = list;
                    listview.setAdapter(new ProductAdapter(getActivity(),data));
                    adapter.notifyDataSetChanged();
                }else {
                    Log.e("qpf","加载数据失败" + e.toString());
                }
            }
        });
    }

    @Override
    protected MyBaseAdapter<Product> getAdapter() {
        return new ProductAdapter(getActivity(),data);
    }
}
