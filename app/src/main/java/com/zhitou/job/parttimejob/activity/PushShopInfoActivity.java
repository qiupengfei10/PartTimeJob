package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.adapter.DiscountsAdapter;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.HomeShop;
import com.zhitou.job.parttimejob.been.MyUser;
import com.zhitou.job.parttimejob.been.ShopDiscounts;
import com.zhitou.job.parttimejob.utils.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 上传店铺信息
 */
public class PushShopInfoActivity extends BaseActivity{

    private PopupWindow popupWindow;
    private ImageView mIvShopLogo;
    private Map<EditText,String> map = new HashMap<>();
    private boolean isPass = true;
    private String path;
    private EditText mEdtShopName;
    private EditText mEdtShopSchool;
    private EditText mEdtAddress;
    private EditText mEdtMininumConsue;
    private EditText mEdtPostage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_shop_info);
        initView();

        mTvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传店铺资料
                if (path== null || path.equals("")){
                    showToast("请上传店铺logo");
                    return;
                }else {
                    //上传logo
                    pushImage(path);
                }
            }
        });
    }

    private void initView() {
        mEdtShopName = (EditText)findViewById(R.id.edt_shop_name);
        mEdtShopSchool = (EditText)findViewById(R.id.edt_shop_school);
        mEdtAddress = (EditText)findViewById(R.id.edt_shop_address);
        mEdtMininumConsue = (EditText)findViewById(R.id.edt_mininum_consume);
        mEdtPostage = (EditText)findViewById(R.id.edt_postage);

        map.put((EditText)findViewById(R.id.edt_shop_name),"店铺名称");
        map.put((EditText)findViewById(R.id.edt_shop_school),"所在学校");
        map.put((EditText)findViewById(R.id.edt_shop_address),"所在寝室");
        map.put((EditText)findViewById(R.id.edt_mininum_consume),"最低消费");
        map.put((EditText)findViewById(R.id.edt_postage),"配送费");

        setTitle("创建店铺");
        mTvTitleRight.setText("下一步");
        mTvTitleRight.setVisibility(View.VISIBLE);

        mIvShopLogo = (ImageView) findViewById(R.id.iv_shop_logo);

    }

    public void click(View view){
        switch (view.getId()){
            case R.id.iv_shop_logo:  //上传logo
                showPopupWindow();
                break;
            case R.id.tv_camera:  // 打开相机
                ImageUtils.openCamera(this,OPEN_CAMERA);
                popupWindow.dismiss();
                break;
            case R.id.tv_album:  //打开相册
                ImageUtils.openAlbum(this,OPEN_ALBUM);
                popupWindow.dismiss();
                break;
        }
    }


    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(PushShopInfoActivity.this).inflate(R.layout.popuwindow_camera_or_album, null);
        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.style_pop_animation);
        //显示PopupWindow
        View rootview = LayoutInflater.from(PushShopInfoActivity.this).inflate(R.layout.activity_id_image, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
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
                        mIvShopLogo.setImageBitmap(bitmap);
                    }
                    break;
                case OPEN_CAMERA: // 打开相机
                    Bundle bundle = data.getExtras();
                    // 转换图片的二进制流
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    // 设置图片
                    mIvShopLogo.setImageBitmap(bitmap);
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
                                mIvShopLogo.setImageBitmap(bm);
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

    /**
     * 将图片上传到服务器
     * @param path
     */
    public void pushImage(String path){
        //判断是否选择照片
        if (path == null){
            showToast("请上传店铺logo！");
            return;
        }
        //店铺图片上传成功
        Set<EditText> key = map.keySet();
        for (EditText k:key) {
            if (k.getText().toString().trim().equals("")){
                showToast("请输入" + map.get(k) + "!");
                isPass = false;
                return;
            }
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    String imageUrl = bmobFile.getFileUrl();
                    //上传店铺信息
                    final HomeShop shop = new HomeShop(imageUrl,mEdtShopName.getText().toString().trim());
                   shop.setUser_id(BmobUser.getCurrentUser(MyUser.class).getObjectId());
                    shop.setAddress(mEdtAddress.getText().toString().trim());
                    shop.setMininum_consume(mEdtMininumConsue.getText().toString().trim());
                    shop.setPostage(mEdtPostage.getText().toString().trim());
                    shop.setSchool(mEdtShopSchool.getText().toString().trim());
                    ArrayList<ShopDiscounts> discountses = new ArrayList<>();
                    discountses.add(new ShopDiscounts(0,"新","满20减2元","20","2"));
                    shop.setDiscountsList(discountses);
                    shop.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null){
                                showToast("创建店铺成功！");
                                //发布商品
                                Intent intent = new Intent(PushShopInfoActivity.this,PushProductInfoActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

}
