package com.zhitou.job.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhitou.job.main.been.TwoHand;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.activity.LoginActivity;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 发布二手页面
 */
public class PushTwoHandActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_two_hand);

        findViewById(R.id.tv_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sava();
            }
        });
    }

    private void sava() {
        if (BmobUser.getCurrentUser(MyUser.class) == null){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        TwoHand twoHand = new TwoHand();
        twoHand.setAddress("地址");
        twoHand.setContent("内容");
        twoHand.setTitle("标题");
        twoHand.setPrice("价格");
        twoHand.setImages("头像");
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
}
