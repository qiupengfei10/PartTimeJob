package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.utils.ImageUtils;

/**
 * 上传商品信息
 */
public class PushProductInfoActivity extends BaseActivity{

    private String path;
    private ImageView mIvProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_product_info);
        initView();
    }

    private void initView() {
        mIvProductImage = (ImageView) findViewById(R.id.iv_product_image);
    }


    public void click(View view){
        switch (view.getId()){
            case R.id.iv_open_camera:
                ImageUtils.showPopupWindow(this);
                break;
            case R.id.tv_camera:  // 打开相机
                ImageUtils.openCamera(this,OPEN_CAMERA);
                ImageUtils.dismissPopupWindow();
                break;
            case R.id.tv_album:  //打开相册
                ImageUtils.openAlbum(this,OPEN_ALBUM);
                ImageUtils.dismissPopupWindow();
                break;
            case R.id.tv_cancel:  //取消
                ImageUtils.dismissPopupWindow();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            switch (requestCode) {
                case OPEN_ALBUM:  //打开相册
                    if (data != null && data.getData() != null){
                        Uri uri = data.getData();
                        path = ImageUtils.getPath(this, uri);
                        Bitmap bitmap = ImageUtils.getLoacalBitmap(path);
                        mIvProductImage.setImageBitmap(bitmap);
                    }
                    break;
                case OPEN_CAMERA: // 打开相机
                    Bundle bundle = data.getExtras();
                    // 转换图片的二进制流
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    // 设置图片
                    mIvProductImage.setImageBitmap(bitmap);
//
                    //用户点击了取消
                    if(data == null){
                        return;
                    }else{
                        Bundle extras = data.getExtras();
                        if (extras != null){
                            //获得拍的照片
                            Bitmap bm = extras.getParcelable("data");
                            //将bitmap存入本地
                            Uri uri = ImageUtils.saveBitmap(bm,"zckj");
                            if (uri != null){
                                path = ImageUtils.getPath(this, uri);
                                mIvProductImage.setImageBitmap(bm);
                                Log.e("qpf","图片路径 ：" + path);
                            }else {
                                Log.e("qpf","图片保存失败!");
                            }
                        }
                    }
                    break;
            }
        }
    }



}
