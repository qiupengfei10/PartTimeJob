package com.zhitou.job.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.main.fragment.ImageViewFragment;
import com.zhitou.job.main.utils.CommonUtils;
import com.zhitou.job.parttimejob.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by yinyanyang on 2018/4/25.
 *
 * 查看大图
 */

public class WatchImageActivity extends BaseActivity implements View.OnClickListener{
    ViewPager mVpImage;
    TextView mTvPhotoNum;
    ImageView mIvSavaPic;

    private Fragment[] fragments;
    private int position;
    private List<String> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_image);
        position = getIntent().getIntExtra("position",1);
        imagePaths = (List<String>) getIntent().getSerializableExtra("imagePaths");

        mVpImage = (ViewPager) findViewById(R.id.vp_image);
        mTvPhotoNum = (TextView) findViewById(R.id.a_browse_photo_tv);
        mIvSavaPic = (ImageView) findViewById(R.id.a_browse_photo_save_img);

        //如果不是网络图片就不能保存
        if (!imagePaths.get(position).contains("http")){
            mIvSavaPic.setVisibility(View.GONE);
        }

        mTvPhotoNum.setText((position + 1) +"/"+imagePaths.size());

        fragments = new Fragment[imagePaths.size()];

        for (int i = 0;i < imagePaths.size();i++){
            fragments[i] = new ImageViewFragment(imagePaths.get(i));
        }
        mVpImage.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }
            @Override
            public int getCount() {
                return fragments.length;
            }
        });

        mVpImage.setCurrentItem(position);


        mIvSavaPic.setOnClickListener(this);

        mVpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                WatchImageActivity.this.position = position;
                mTvPhotoNum.setText((position + 1) +"/"+imagePaths.size());
                Log.e("qpf","图片的地址 -- " + imagePaths.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a_browse_photo_save_img:
                CommonUtils.showToast("保存到相册...", this);
                //判断是否有读写权限
                savaPicForUrl(imagePaths.get(position));
                break;
            default:
                break;
        }
    }

    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径
     * @return void
     */
    public void SavaImage(Bitmap bitmap, String path){
        File file=new File(path);
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            fileOutputStream=new FileOutputStream(path+"/"+ System.currentTimeMillis() + CommonUtils.getRandomString(0)+".png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            fileOutputStream.close();
            CommonUtils.showToast("保存图片完成！",this);
            Log.e("qpf",path+"/"+ System.currentTimeMillis() + CommonUtils.getRandomString(0)+".png");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("qpf","保存图片失败 -- " + e.toString());
        }
    }


    public void savaPicForUrl(final String imageUrl){
        AsyncTask<String,String,Bitmap> asyncTask = new AsyncTask<String, String, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                return GetImageInputStream(imageUrl);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                //保存图片在本地
//                SavaImage(bitmap,Environment.getExternalStorageDirectory().getPath()+"/miaoji");
                saveImageToGallery(WatchImageActivity.this,bitmap);
            }
        }.execute();
    }


    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     *
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * @param bmp 获取的bitmap数据
     */

    //保存文件到指定路径
    public boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "miaoji";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                CommonUtils.showToast("保存成功！",WatchImageActivity.this);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
