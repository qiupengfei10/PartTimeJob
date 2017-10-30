package com.zhitou.job.parttimejob.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.CellSignalStrengthWcdma;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.base.MyApplication;
import com.zhitou.job.parttimejob.been.MyUser;
import com.zhitou.job.parttimejob.utils.ImageUtils;

import java.io.File;
import java.net.URL;
import java.text.BreakIterator;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * 输入身份
 */
public class IDImageActivity extends BaseActivity {

    private boolean upLoading = false;
    private static final int REQUEST_CODE = 100;
    private int position = 0;

    private ImageView mIvIDCard1;
    private ImageView mIvIDCard2;
    private ImageView[] mIvIDCards = new ImageView[2];

    private PopupWindow popupWindow;
    private MyUser user;
    private String mFilePath;

    private final int OPEN_CAMERA = 0;
    private final int OPEN_ALBUM = 1;
    private String[] paths = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_image);
        //检测权限是否开启
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    0);
            showDialogTwoBtn("请开启拍照权限在进行后续操作！", "暂不开启", "立即开启", new OnClickListenerForDialogTwoBtn() {
                @Override
                public void onClickListenerForDialog(TextView tvBtn1, TextView tvBtn2) {

                    tvBtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           finish();
                            dismissAler();
                        }
                    });
                    tvBtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                            dismissAler();
                        }
                    });
                }
            });
        }else {
            showToast("已开通权限");
        }

        initView();
    }

    private void initView() {
        setTitle("实名认证");
        mIvIDCards = new ImageView[]{(ImageView)findViewById(R.id.iv_id_card1),(ImageView)findViewById(R.id.iv_id_card2)};

        findViewById(R.id.tv_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upLoading){
                    showToast("正在上传...");
                    return;
                }
                upLoading = true;
                pushImage(paths);
            }
        });
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.iv_id_card1:
                showPopupWindow();
                position = 0;
                break;
            case R.id.iv_id_card2:
                showPopupWindow();
                position = 1;
                break;
            case R.id.tv_push:
                popupWindow.dismiss();
                break;
            case R.id.tv_camera:  // 打开相机
                openCamera();
                popupWindow.dismiss();
                break;
            case R.id.tv_album:  //打开相册
                openAlbum();
               popupWindow.dismiss();
                break;
            case R.id.tv_cancel:  //取消
                popupWindow.dismiss();
                break;
        }

    }

    private void openAlbum() {
        ImageUtils.openAlbum(this,OPEN_ALBUM);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            switch (requestCode) {
                case OPEN_ALBUM:  //打开相册
                    if (data != null && data.getData() != null){
                        Uri uri = data.getData();
                        String path = ImageUtils.getPath(this, uri);
                        Log.e("qpf","图片路径 ：" + path);
                        paths[position] = path;
                        Bitmap bitmap = ImageUtils.getLoacalBitmap(path);
                        mIvIDCards[position].setImageBitmap(bitmap);
                    }
                    break;
                case OPEN_CAMERA: // 打开相机
                    Bundle bundle = data.getExtras();
                    // 转换图片的二进制流
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    // 设置图片
                    mIvIDCards[position].setImageBitmap(bitmap);
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
                                String path = ImageUtils.getPath(this, uri);
                                paths[position] = path;
                                mIvIDCards[position].setImageBitmap(bm);
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

    public void openCamera() {
        ImageUtils.openCamera(this,OPEN_CAMERA);
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

    /**
     * 将图片上传到服务器
     * @param paths
     */
    public void pushImage(String[] paths){
        //判断是否选择照片
        if (paths[0] == null){
            showToast("请上传“身份证头像面”的照片！");
            return;
        }
        if (paths[1] == null){
            showToast("请上传“身份证国徽面”的照片！");
            return;
        }
        BmobFile.uploadBatch(paths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
               if (list.size() == 2){
                   user = BmobUser.getCurrentUser(MyUser.class);
                   Log.e("qpf","userId" + user.getObjectId());
//                   MyUser newUser = new MyUser();
                   user.setIs_approve(APPLY_FOR);
                   user.setIDCard1(list.get(0).getFileUrl());
                   user.setIDCard2(list.get(1).getFileUrl());

                   user.update(new UpdateListener() {
                       @Override
                       public void done(BmobException e) {
                           if (e == null){
                               Intent intent = new Intent(IDImageActivity.this,ApplyActivity.class);
                               intent.putExtra("status","您已成功提交审核，请耐心等候结果。");
                               intent.putExtra("hint","我们将在1-3个工作日内完成审核，届时将通过手机短信的方式将结果通知到您！");
                               startActivity(intent);
                           }else {
                               showToast("上传用户信息失败，请重试！");
                               Log.e("qpf","上传用户信息失败 " + e.toString());
                               upLoading = false;
                           }
                       }
                   });
               }

            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
            }

            @Override
            public void onError(int i, String s) {
                upLoading = false;
            }
        });

    }
}
