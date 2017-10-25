package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.base.MyApplication;
import com.zhitou.job.parttimejob.been.MyUser;
import com.zhitou.job.parttimejob.utils.ImageUtils;

/**
 * 输入身份
 */
public class IDImageActivity extends BaseActivity {

    private ImageView mIvIDCard1;
    private ImageView mIvIDCard2;

    private PopupWindow popupWindow;
    private MyUser user;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_image);
        user = MyApplication.getInstance().getUser();
        initView();
    }

    private void initView() {
        setTitle("实名认证");
        mIvIDCard1 = (ImageView)findViewById(R.id.iv_id_card1);
        mIvIDCard2 = (ImageView)findViewById(R.id.iv_id_card2);
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.iv_id_card1:
                showPopupWindow();
                break;
            case R.id.iv_id_card2:
                showPopupWindow();
                break;
            case R.id.tv_push:
                showPopupWindow();
                break;
            case R.id.tv_camera:  // 打开相机
                openCamera(1);
                break;
            case R.id.tv_album:  //打开相册
                openAlbum();
                break;
            case R.id.tv_cancel:  //取消
                popupWindow.dismiss();
                break;
        }

    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            switch (requestCode){
                case 0:
                    String path = ImageUtils.getPath(this,data.getData());
                    Log.e("qpf","图片路径：" + path);
                    Bitmap bitmap1 = ImageUtils.getLoacalBitmap(path);
                    mIvIDCard2.setImageBitmap(bitmap1);// 显示图片
                    break;
                case 1:
                    mFilePath = Environment.getExternalStorageDirectory().getPath();// 获取SD卡路径
                    mFilePath = mFilePath + "/" + "IDcard"+System.currentTimeMillis()+".png";// 指定路径
                    Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                    Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
//                    ImageUtils.saveMyBitmap(bitmap,mFilePath);
                    mIvIDCard1.setImageBitmap(bitmap);// 显示图片
                    break;
            }
        }
    }

    public void openCamera(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, 1);
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(IDImageActivity.this).inflate(R.layout.popuwindow_camera_or_album, null);
        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.style_pop_animation);
        //显示PopupWindow
        View rootview = LayoutInflater.from(IDImageActivity.this).inflate(R.layout.activity_id_image, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
}
