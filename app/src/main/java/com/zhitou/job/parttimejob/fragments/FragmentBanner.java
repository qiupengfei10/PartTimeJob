package com.zhitou.job.parttimejob.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhitou.job.R;
import com.zhitou.job.parttimejob.been.HomeBanner;

/**
 * Created by qiupengfei on 2017/7/13.
 */
@SuppressLint("ValidFragment")
public class FragmentBanner extends Fragment{

    private View view;
    private ImageView mIvBanner;
    private String url;

    public FragmentBanner(HomeBanner homeBanner){
        url = homeBanner.getType();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_banner,null);
        mIvBanner = (ImageView) view.findViewById(R.id.iv_banner);
        Glide.with(getActivity()).load(url).into(mIvBanner);
        Log.e("qpf",url);
        return view;
    }

}
