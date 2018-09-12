package com.zhitou.job.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zhitou.job.R;
import com.zhitou.job.main.adapter.PushImageAdapter;
import com.zhitou.job.main.been.ImageBeen;
import com.zhitou.job.main.been.TwoHand;
import com.zhitou.job.main.utils.AddressUtils;
import com.zhitou.job.parttimejob.activity.LoginActivity;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * 发布二手页面
 */
public class PushTwoHandActivity extends BaseActivity{
    public static final int IMAGE_PICKER = 1;

    private EditText mEdtTitle;
    private RecyclerView mRecycler;
    private EditText mEdtContent;
    private EditText mEdtPrice;
    private TextView mTvAddress;
    private String title;
    private String content;
    private String price;
    private String[] images = new String[]{
            "https://gss0.bdstatic.com/70cFfyinKgQIm2_p8IuM_a/daf/pic/item/d833c895d143ad4b164d6bea8f025aafa40f06bc.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2875653198,2481124055&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3034550788,1048580024&fm=200&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=919673108,713933726&fm=200&gp=0.jpg",
            "https://ss0.bdstatic.com/-0U0b8Sm1A5BphGlnYG/kmarketingadslogo/fb51462a975cc4a1f5e3d075ec98e74c_259_194.jpg",
    };
    private String city;
    private String province;
    private String area;
    private ArrayList<ImageBeen> pushImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_two_hand);
        initView();

        AddressUtils.getPCD(this,new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                aMapLocation.getCity();
                province = aMapLocation.getProvince();
                city = aMapLocation.getCity();
                area = aMapLocation.getDistrict();
                mTvAddress.setText(city + " " + area);
                log("地理位置 --- " + province + "/" + city + "/" + area);
                log("地理位置错误 --- " + aMapLocation.getErrorCode());
            }
        });
    }

    private boolean verifyEdt(){

        title = mEdtTitle.getText().toString().trim();
        content = mEdtContent.getText().toString().trim();
        price = mEdtPrice.getText().toString().trim();

        if (title.trim().equals("")){
            showToast("请输入标题！");
            return false;
        }
        if (content.trim().equals("")){
            showToast("请输入详细描述！");
            return false;
        }
        if (price.trim().equals("")){
            showToast("请输入出售价格！");
            return false;
        }

        if (pushImageList.size() < 3){
            showToast("至少上传3张商品图片！");
            return false;
        }

        if (BmobUser.getCurrentUser(MyUser.class) == null){
            startActivity(new Intent(this, LoginActivity.class));
            return false;
        }

        return true;
    }

    private void initView() {
        findViewById(R.id.tv_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyEdt()){
//                    sava();
                    pushImage(pushImageList);
                }
            }
        });

        findViewById(R.id.ll_open_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PushTwoHandActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });

        mEdtTitle = (EditText)findViewById(R.id.edt_title);  //标题
        mEdtContent = (EditText)findViewById(R.id.edt_content); //内容
        mEdtPrice = (EditText)findViewById(R.id.edt_price); // 价格
        mTvAddress = (TextView) findViewById(R.id.tv_address); // 价格
        mRecycler = (RecyclerView) findViewById(R.id.recyclerview); // 价格

        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

    }

    private void sava() {
        TwoHand twoHand = new TwoHand();
        twoHand.setAddress(city+area);
        twoHand.setContent(content);
        twoHand.setTitle(title);
        twoHand.setPrice(price);
        twoHand.setImageBeens(pushImageList);
        twoHand.setImages(images[(int) (Math.random() * 4)]+";"+images[(int) (Math.random() * 4)]+";"+images[(int) (Math.random() * 4)]);
        twoHand.setSub("分类");
        twoHand.setPushUser(BmobUser.getCurrentUser(MyUser.class));
        twoHand.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    showToast("保存成功！");
                }else {
                    showToast("保存失败！" + e);
                }
            }
        });
    }

    //上传图片
    public void pushImage(final List<ImageBeen> pushImageList){
        final String[] paths = new String[pushImageList.size()];
        for (int i = 0;i < pushImageList.size();i++){
            paths[i] = pushImageList.get(i).getPath();
        }

        //详细示例可查看BmobExample工程中BmobFileActivity类
        BmobFile.uploadBatch(paths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files,List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if(urls.size()==paths.length){//如果数量相等，则代表文件全部上传完成
                    //do something
                    showToast("上传图片完成！");
                    for (int i = 0; i < paths.length;i++){
                        Log.e("qpf","图片原始地址 -- " + paths[i] + " --- 图片的网络地址 --- " + files.get(i).getFilename());

                        if (pushImageList.get(i).getPath().equals(files.get(i).getFilename())){
                            pushImageList.get(i).setPath(urls.get(i));
                        }else {
                            for (int j = 0;j < pushImageList.size();j++){
                                if (pushImageList.get(i).getPath().equals(files.get(i).getFilename())){
                                    pushImageList.get(i).setPath(urls.get(i));
                                }
                            }
                        }
                        pushImageList.get(i).save();
                    }

                    sava();

                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                showToast("错误码"+statuscode +",错误描述："+errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if ((requestCode == IMAGE_PICKER)) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0;i < images.size();i++){
                    ImageItem image = images.get(i);
                    pushImageList.add(new ImageBeen(image.width + "",image.height + "",image.path,((double)image.height/(double) image.width) + ""));
                }

                if (pushImageList.size() > 0){
                    //返回的图片数量
                    Log.e("qpf","选择的图片数量 -- " + images.size());
                    mRecycler.setAdapter(new PushImageAdapter(this, pushImageList, new PushImageAdapter.OnDeleteListener() {
                        @Override
                        public void onDeleteListener(int position) {
                            if (pushImageList.size() == 0){
                                mRecycler.setVisibility(View.GONE);
                            }
                            log("图片的数量 -- " + pushImageList.size());
                        }
                    }));
                    mRecycler.setVisibility(View.VISIBLE);
                }

            }
        }
    }

}
