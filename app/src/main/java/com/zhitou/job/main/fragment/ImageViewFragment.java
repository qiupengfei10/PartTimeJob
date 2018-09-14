package com.zhitou.job.main.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhitou.job.R;
import com.zhitou.job.main.utils.GlideUtils;
import com.zhitou.job.main.view.TouchImageView;


/**
 * Created by yinyanyang on 2018/4/25.
 */

@SuppressLint("ValidFragment")
public class ImageViewFragment extends Fragment {

    private View view;
    private String imagePath;
    private TouchImageView imageView;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (getActivity() != null)
                    getActivity().finish();
                    break;
            }
        }
    };

    public ImageViewFragment(String imagePath){
        this.imagePath = imagePath;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =LayoutInflater.from(getActivity()).inflate(R.layout.imageview_fragment, null);
        imageView = (TouchImageView)view.findViewById(R.id.imageview);
        imageView.setHandler(handler);

        Log.e("qpf","imagepath -- " + imagePath);

        GlideUtils.showPicPlaceholderAndError(getContext(),imagePath, R.mipmap.default_image, R.mipmap.default_image,imageView);
//        Glide.with(this).load(imagePath).placeholder(R.mipmap.default_image).dontAnimate().into(imageView);

        return view;
    }
}
