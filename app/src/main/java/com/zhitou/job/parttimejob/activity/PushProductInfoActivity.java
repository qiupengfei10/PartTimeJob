package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhitou.job.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.Product;
import com.zhitou.job.parttimejob.been.ProductClassify;
import com.zhitou.job.parttimejob.constant.Constant;
import com.zhitou.job.parttimejob.utils.ImageUtils;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 上传商品信息
 */
public class PushProductInfoActivity extends BaseActivity{

    private String shop_id;
    private String path;
    private ImageView mIvProductImage;
    private TextView mTvProductSub;
    private Intent intent;
    private TextView mTvProductDetail;

    private String productDetail;
    private ProductClassify sub;

    private Map<TextView,String> map = new HashMap<>();
    private EditText mEdtProductName;
    private EditText mEdtProductPrice;
    private EditText mEdtProductNumber;
    private boolean isPass = true;
    private Product product;
    private String from;
    private TextView mTvPush;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_product_info);
        from = getIntent().getStringExtra("from");
        shop_id = getIntent().getStringExtra("shop_id");
        //编辑商品传过来的
        product = (Product)getIntent().getSerializableExtra("product");
        initView();
    }

    private void initView() {
        setTitle("发布商品");
        mIvProductImage = (ImageView) findViewById(R.id.iv_product_image);
        mTvProductSub = (TextView) findViewById(R.id.tv_product_sub);
        mTvProductDetail = (TextView) findViewById(R.id.tv_product_detail);
        mEdtProductName = (EditText)findViewById(R.id.edt_product_name);
        mEdtProductPrice = (EditText)findViewById(R.id.edt_product_price);
        mEdtProductNumber = (EditText)findViewById(R.id.edt_product_number);
        mTvPush = (TextView)findViewById(R.id.tv_push);

        map.put(mEdtProductName,"商品名称");
        map.put(mTvProductSub,"类目");
        map.put(mEdtProductPrice,"价格");
        map.put(mEdtProductNumber,"库存");
        map.put(mTvProductDetail,"商品描述");

        //
        if (product != null){
            setTitle("编辑商品");
            Glide.with(this).load(product.getImage_url()).into(mIvProductImage);
            mEdtProductName.setText(product.getName());
            mTvProductSub.setText("修改类目");
            mEdtProductPrice.setText(product.getPrice() + "");
            mEdtProductNumber.setText(product.getSale() + "");
            mTvProductDetail.setText("已编辑");
            mTvPush.setText("更新");
        }
    }


    public void click(View view){
        switch (view.getId()){
            case R.id.tv_push:
                //上传商品
                pushProduct();
                break;
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
            case R.id.tv_product_sub:  //选择类目
                intent = new Intent(this,SelectSubActivity.class);
                intent.putExtra("shop_id",shop_id);
                startActivityForResult(intent,10);
                break;
            case R.id.tv_product_detail:  //商品描述
                intent = new Intent(this,ProductDetailTextActivity.class);
                if (productDetail != null && !productDetail.equals("")){
                    intent.putExtra("detail",productDetail);
                }
                startActivityForResult(intent,11);
                break;
        }
    }

    private void pushProduct() {
        //上传图片
        if (path == null || path.equals("")){
            showToast("请上传商品图片！");
            return;
        }

        //店铺图片上传成功
        Set<TextView> key = map.keySet();
        for (TextView k:key) {
            if (k.getText().toString().trim().equals("")){
                k.setHint("请填写"+map.get(k));
                k.setHintTextColor(getResources().getColor(R.color.main_color));
                isPass = false;
            }
        }

        if (!isPass){
            return;
        }

        final BmobFile file = new BmobFile(new File(path));
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                //图片上传成功
                if (e == null){
                    Product product = new Product();
                    if (Constant.PRODUCT_MANAGE_EDT.equals("from")){
                        product = PushProductInfoActivity.this.product;
                    }
                    product.setImage_url(file.getFileUrl());
                    product.setName(mEdtProductName.getText().toString().trim());
                    product.setPrice(Double.valueOf(mEdtProductPrice.getText().toString().trim()));
//                    product.setba
                    product.setStatus(productDetail);
                    product.setShop_id(shop_id);
                    product.setClassify_id(sub.getObjectId());
                    //上传商品
                    product.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null){
                                showToast("上传商品完成");
                            }
                        }
                    });

                }else {
                    showToast("图片上传错误！");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("resultCode == " + resultCode);
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
                case 10:
                    sub = (ProductClassify) data.getSerializableExtra("sub");
                    mTvProductSub.setText(sub.getSubject());
                    break;
                case 11:
                    productDetail = data.getStringExtra("detail");
                    if (productDetail!= null && !productDetail.equals("")){
                        mTvProductDetail.setText("已编辑");
                    }
                    break;
            }
        }
    }



}
